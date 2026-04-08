# Logger en JSON avec Spring Boot

## Pourquoi logger en JSON

Le JSON est surtout utile quand les logs sont consommés par des outils.

Par exemple :

* Elasticsearch / Kibana
* Logstash
* Fluent Bit
* Filebeat
* Grafana Loki
* Graylog

L’intérêt principal :

* les logs deviennent structurés
* les champs sont parsables automatiquement
* la recherche et le filtrage sont beaucoup plus simples

En microservices, c’est souvent plus utile qu’un log texte classique.

---

## La solution la plus simple avec Spring Boot récent

Sur un Spring Boot récent, le plus simple est d’utiliser la structured logging native.

### En `application.yml`

```yaml
logging:
  structured:
    format:
      console: ecs
      file: logstash
  file:
    name: logs/application.json
```

### En `application.properties`

```properties
logging.structured.format.console=ecs
logging.structured.format.file=logstash
logging.file.name=logs/application.json
```

---

## Les formats utiles

`ecs`
Très bon choix si la destination est Elastic / Kibana.

`logstash`
Pratique pour des pipelines orientés Logstash.

`gelf`
Pratique si la cible est Graylog.

Dans la plupart des cas :

* ELK -> `ecs`
* Graylog -> `gelf`
* besoin générique -> `logstash`

---

## Console JSON ou fichier JSON

En local, un format texte reste souvent plus lisible.

En Docker / Kubernetes, on préfère généralement :

* JSON sur la console
* pas de fichier local dans le conteneur

Exemple minimal :

```yaml
logging:
  structured:
    format:
      console: ecs
```

Si tu veux aussi un fichier JSON hors conteneur :

```yaml
logging:
  structured:
    format:
      console: ecs
      file: ecs
  file:
    name: logs/application.json
```

---

## Ajouter des champs utiles

Le JSON devient vraiment intéressant si tu ajoutes des champs structurés.

Exemple avec l’API fluente SLF4J :

```java
log.atInfo()
    .addKeyValue("userId", userId)
    .addKeyValue("orderId", orderId)
    .addKeyValue("paymentStatus", status)
    .log("Commande créée");
```

Champs souvent utiles :

* `traceId`
* `spanId`
* `requestId`
* `userId`
* `orderId`
* `service`
* `environment`

👉 Avec du JSON, ces champs sont directement exploitables côté observabilité.

---

## Quand éviter le JSON

Le JSON n’est pas toujours nécessaire.

Tu peux rester en texte simple si :

* le projet est petit
* les logs sont lus uniquement en console locale
* il n’y a pas de pipeline de collecte derrière

En revanche, dès qu’il y a plusieurs services ou une stack d’observabilité, le JSON devient un vrai plus.

---

## JSON avec `logback-spring.xml`

Si tu veux seulement activer du JSON, `application.properties` ou `application.yml` est souvent suffisant.

Le XML devient utile si tu veux :

* mixer console texte et fichier JSON
* cibler plusieurs appenders
* intégrer une configuration Logback plus avancée

Pour la configuration XML, voir [logging-logback-xml.md](/Users/canse/Documents/Code/basic-spring/basic-spring/documentation/logging-logback-xml.md).

---

## Recommandation simple

Si ton application est un microservice :

* local -> texte lisible
* Docker / Kubernetes -> console JSON
* ELK -> JSON `ecs`

---

## Liens officiels

* Spring Boot Logging : https://docs.spring.io/spring-boot/reference/features/logging.html
* Spring Boot structured logging : https://docs.spring.io/spring-boot/reference/features/logging.html#features.logging.structured
* SLF4J fluent API : https://www.slf4j.org/apidocs/org/slf4j/spi/LoggingEventBuilder.html
* ECS Logging Java : https://www.elastic.co/guide/en/ecs-logging/java/current/intro.html

## Article complémentaire

* Baeldung, structured logging Spring Boot : https://www.baeldung.com/spring-boot-structured-logging
