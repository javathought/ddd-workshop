package io.github.javathought.clean.bank.model;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(strict = false,
        plugin = { "pretty",
        "json:target/cucumber.json" },
        tags = { "~@ignore" })
public class BDDRunnerTest {
}
