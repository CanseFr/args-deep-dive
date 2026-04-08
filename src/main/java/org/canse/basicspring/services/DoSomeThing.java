package org.canse.basicspring.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DoSomeThing {

    private String name;

    public DoSomeThing() {
        name = "canse";
    }

    public void printName() {
        System.out.println("-----------------");
        System.out.println("----LOG INFO-----");
        System.out.println("-----------------");
        log.trace("Très détaillé");
        log.debug("Debug");
        log.info("Info générale");
        log.warn("Attention");
        log.error("Erreur");
        System.out.println(name);
    }

    public void createUser(Boolean b) {
        log.info("Création de l'utilisateur: {}", name);

        try {
            log.debug("Utilisateur {} en cours de traitement");
            if (b) {
                throw new Exception();
            }
        } catch (Exception e) {
            log.error("Erreur lors de la création ");
        } finally {
            log.info("Création terminé");
        }
    }

}
