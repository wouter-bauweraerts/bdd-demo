package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.scenarios;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.steps")
public class ProductOverviewScenariosTest {
}
