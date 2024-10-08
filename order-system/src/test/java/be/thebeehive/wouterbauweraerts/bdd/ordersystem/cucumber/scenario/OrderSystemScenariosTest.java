package be.thebeehive.wouterbauweraerts.bdd.ordersystem.cucumber.scenario;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import jakarta.transaction.Transactional;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "be.thebeehive.wouterbauweraerts.bdd.ordersystem.cucumber.steps")
public class OrderSystemScenariosTest {
}
