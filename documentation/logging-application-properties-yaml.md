# Configurer les logs avec `application.properties` ou `application.yml`

## Quand utiliser cette approche

Cette approche est la plus simple avec Spring Boot.

Elle convient très bien pour :

* changer les niveaux de logs
* personnaliser le format console ou fichier
* activer un fichier de logs
* activer le JSON sur un Spring Boot récent

Si tu veux une configuration plus fine avec plusieurs appenders, rotation avancée ou routage spécifique, il faut plutôt passer par `logback-spring.xml`.

---

## Exemple en `application.yml`

```yaml
logging:
  level:
    root: INFO
    com.monapp: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} %clr(%-5level) [%thread] %logger{36} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n"
  file:
    name: logs/application.log
```

## Même chose en `application.properties`

```properties
logging.level.root=INFO
logging.level.com.monapp=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} %clr(%-5level) [%thread] %logger{36} - %msg%n
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n
logging.file.name=logs/application.log
```

---

## Ce que chaque propriété fait

`logging.level.root`
Niveau global par défaut.

`logging.level.com.monapp`
Niveau spécifique pour un package ou une classe. Par exemple :

```properties
logging.level.org.canse.basicspring.services=DEBUG
logging.level.org.canse.basicspring.repositories=TRACE
```

`logging.pattern.console`
Pattern d’affichage dans la console.

`logging.pattern.file`
Pattern d’affichage dans le fichier.

`logging.file.name`
Chemin du fichier de log.

---

## Ce qu’on peut configurer facilement

Avec `properties` ou `yml`, tu peux très bien :

* définir des niveaux par package
* changer le format d’une ligne de log
* colorer la console avec `%clr`
* écrire dans un fichier
* produire du JSON avec la structured logging native de Spring Boot

Ce qu’on ne fait pas bien ici :

* avoir plusieurs fichiers avec des règles différentes
* router un package vers un appender spécifique
* définir une config Logback très avancée

Pour ça, voir [logging-logback-xml.md](/Users/canse/Documents/Code/basic-spring/basic-spring/documentation/logging-logback-xml.md).

---

## JSON

Pour la partie JSON complète, voir :

* [logging-json.md](/Users/canse/Documents/Code/basic-spring/basic-spring/documentation/logging-json.md)

---

## Recommandations pratiques

Pour du local :

* console texte colorée
* éventuellement fichier texte simple

Pour du Docker / Kubernetes :

* privilégier la console
* privilégier le JSON
* éviter les fichiers locaux dans le conteneur

Pour ELK :

* privilégier `ecs`

---

## Liens officiels

* Spring Boot Logging : https://docs.spring.io/spring-boot/reference/features/logging.html
* Spring Boot custom log configuration : https://docs.spring.io/spring-boot/reference/features/logging.html#features.logging.custom-log-configuration
* Spring Boot structured logging : https://docs.spring.io/spring-boot/reference/features/logging.html#features.logging.structured

## Article complémentaire

* Baeldung, structured logging Spring Boot : https://www.baeldung.com/spring-boot-structured-logging
