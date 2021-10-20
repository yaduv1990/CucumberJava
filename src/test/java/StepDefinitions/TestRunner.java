package StepDefinitions;

import org.junit.runner.RunWith;
import org.testng.annotations.DataProvider;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;



@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/Features",
glue={"StepDefinitions"},
monochrome = true,
plugin = { "pretty", "json:target/cucumber-reports/Cucumber.json",
		"junit:target/cucumber-reports/Cucumber.xml",
"html:target/cucumber-reports/htmlreport"},
tags =  "@functional")
public class TestRunner {
		

}
