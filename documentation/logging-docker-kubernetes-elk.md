# Logs avec Docker, Kubernetes et ELK

## Le principe à retenir

En conteneur, la bonne pratique est simple :

* l’application écrit ses logs sur `stdout` / `stderr`
* Docker ou Kubernetes les récupère
* un agent de collecte les envoie vers la stack d’observabilité

👉 Donc, dans un conteneur, on évite en général d’écrire les logs applicatifs dans un fichier local.

---

## Avec Docker

Le plus simple :

* garder les logs en console
* laisser Docker les collecter
* consulter avec `docker logs`

Exemple Spring Boot :

```yaml
logging:
  structured:
    format:
      console: ecs
```

Pourquoi c’est bien :

* pas de volume à gérer juste pour les logs
* les logs restent visibles immédiatement
* c’est compatible avec la plupart des log drivers Docker

Si tu écris dans un fichier dans le conteneur :

* tu compliques la rotation
* tu compliques la collecte
* tu risques de perdre les logs au redémarrage

---

## Avec Kubernetes

Kubernetes attend lui aussi que les applications loggent vers `stdout` / `stderr`.

Flux classique :

1. l’application écrit en console
2. le runtime du conteneur stocke les logs
3. `kubectl logs` permet de les lire
4. un agent type Fluent Bit / Filebeat / Vector les expédie vers Elasticsearch, Loki, etc.

👉 En pratique :

* console JSON si possible
* pas de fichier local pour les logs métier
* enrichir les logs avec des champs structurés (`traceId`, `spanId`, `service`, `userId`, etc.)

Exemple utile :

```yaml
logging:
  structured:
    format:
      console: ecs
```

---

## Avec ELK stack

Pour ELK, le plus propre est d’émettre directement du JSON structuré.

Le meilleur choix ici est souvent `ecs` :

```yaml
logging:
  structured:
    format:
      console: ecs
```

Pourquoi `ecs` est intéressant :

* format aligné avec Elastic Common Schema
* indexation plus simple dans Elasticsearch
* dashboards Kibana plus faciles à exploiter
* meilleure corrélation avec traces et métriques

Chaîne typique :

1. Spring Boot écrit les logs JSON ECS
2. Fluent Bit / Filebeat les collecte
3. Elasticsearch les indexe
4. Kibana les visualise

---

## Recommandation simple

Si ton application tourne en microservices :

* utilise `SLF4J` + Logback via Spring Boot
* logge en JSON sur la console
* préfère `ecs` si la destination est ELK
* ajoute des champs structurés plutôt que de parser du texte libre

En bref :

* machine locale / dev : format texte coloré
* Docker / Kubernetes : console JSON
* ELK : JSON `ecs`

---

## Liens officiels

* Spring Boot Logging : https://docs.spring.io/spring-boot/reference/features/logging.html
* Spring Boot structured logging : https://docs.spring.io/spring-boot/reference/features/logging.html#features.logging.structured
* Docker logging : https://docs.docker.com/engine/logging/
* Kubernetes logging architecture : https://kubernetes.io/docs/concepts/cluster-administration/logging/
* ECS Logging Java : https://www.elastic.co/guide/en/ecs-logging/java/current/intro.html

## Article complémentaire

* Baeldung, structured logging Spring Boot : https://www.baeldung.com/spring-boot-structured-logging
