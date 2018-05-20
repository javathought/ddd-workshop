package io.github.javathought.clean.bank.model;

import cucumber.api.PendingException;
import cucumber.api.java8.En;

public class WithdrawStepdefs implements En {
    public WithdrawStepdefs() {
        When("^je transfère (\\d+),(\\d+) EUR du compte 'T(\\d+)' vers le compte 'C(\\d+)'$", (Integer arg0, Integer arg1, Integer arg2, Integer arg3) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^l'opération est en attente$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^l'opération est annulée$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        When("^je transfère (\\d+),(\\d+) EUR du compte 'T(\\d+)' vers le compte 'I(\\d+)'$", (Integer arg0, Integer arg1, Integer arg2, Integer arg3) -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^la banque destinatrice renvoie la réponse négative 'Compte inexistant'$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
        And("^la banque destinatrice renvoie la réponse 'Opération exécutée'$", () -> {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        });
    }
}
