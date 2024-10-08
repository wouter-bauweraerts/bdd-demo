package be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

import org.awaitility.Awaitility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import com.github.tomakehurst.wiremock.WireMockServer;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductCatalogWiremockStub {

    WireMockServer wm = new WireMockServer(options().dynamicPort());

    @Autowired
    private ConfigurableApplicationContext ctx;

    @PostConstruct
    public void initializeProductCatalog() {
        wm.start();
        Awaitility.await("Wiremock").untilAsserted(() -> assertThat(wm.isRunning()).isTrue());
        log.info("Wiremock server port: {}", wm.port());
        ctx.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                if (event instanceof ContextClosedEvent) {
                    log.info("Closing context. Stopping Wiremock");
                    wm.stop();
                    log.info("Wiremock stopped");
                }
            }
        });
    }

    public void stubProductNotFound(int productId) {
        wm.stubFor(get(urlPathEqualTo("api/product-overview/" + productId))
                .willReturn(aResponse().withStatus(404)));
    }

    public void reset() {
        wm.resetMappings();
    }
}
