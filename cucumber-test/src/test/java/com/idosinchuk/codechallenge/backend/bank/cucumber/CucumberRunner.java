package com.idosinchuk.codechallenge.backend.bank.cucumber;

import com.idosinchuk.codechallenge.backend.bank.cucumber.util.Environment;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@Slf4j
@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "de.monochromata.cucumber.report.PrettyReports:target/cucumber" },
        features = "classpath:com.idosinchuk.codechallenge.backend.bank.cucumber",
        tags = "not @ignore"
)
public class CucumberRunner {
    @BeforeClass
    public static void checkTestEnvironment() {
        Environment environment = Environment.getCurrent();
        log.info("The test suite is prepared to run on {} environment", environment.name());
    }
}