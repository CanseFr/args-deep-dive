# Configurer les logs avec `logback-spring.xml`

## Quand utiliser cette approche

Utilise `logback-spring.xml` quand `application.properties` ou `application.yml` ne suffit plus.

Par exemple si tu veux :

* plusieurs appenders
* un fichier dédié pour certains logs
* une rotation avancée
* un format différent selon la destination
* une configuration Logback plus fine

---

## Où placer le fichier

Le plus courant est :

* `src/main/resources/logback-spring.xml`

Spring Boot le détecte automatiquement au démarrage.

Le suffixe `-spring` est utile car Spring Boot peut y gérer ses extensions plus proprement que dans un `logback.xml` brut.

---

## Exemple minimal

```xml
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} %highlight(%-5level) [%thread] %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/application.log</file>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
    </root>
</configuration>
```

---

## Exemple avec package spécifique

Tu peux régler le niveau d’un package précis :

```xml
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d %-5level [%thread] %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <logger name="org.canse.basicspring.services" level="DEBUG"/>

    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
    </root>
</configuration>
```

Ici :

* tout le reste reste en `INFO`
* `org.canse.basicspring.services` passe en `DEBUG`

---

## Exemple avec fichier dédié pour un package

```xml
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="REPOSITORY_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/repository.log</file>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <logger name="org.canse.basicspring.repository" level="DEBUG" additivity="false">
        <appender-ref ref="REPOSITORY_FILE"/>
    </logger>

    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
    </root>
</configuration>
```

👉 Là, `org.canse.basicspring.repository` part dans un fichier dédié.

`additivity="false"` évite que ces mêmes logs remontent aussi vers le root logger si tu ne le souhaites pas.

---

## Rotation des fichiers

Si tu écris dans un fichier, pense à la rotation.

Exemple simple :

```xml
<appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <file>logs/application.log</file>

    <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
        <fileNamePattern>logs/application.%d{yyyy-MM-dd}.log</fileNamePattern>
        <maxHistory>7</maxHistory>
    </rollingPolicy>

    <encoder>
        <pattern>%d %-5level [%thread] %logger{36} - %msg%n</pattern>
    </encoder>
</appender>
```

---

## JSON avec XML

Si tu veux juste du JSON sur Spring Boot récent, la config `properties` / `yml` est souvent plus simple.

Le XML devient utile si tu veux :

* mixer console texte et fichier JSON
* cibler certains appenders
* brancher un encoder JSON spécifique

Dans ce projet, vu que Spring Boot est récent, commence par la structured logging native avant d’ajouter une couche Logback plus complexe.

Pour la partie JSON complète, voir :

* [logging-json.md](/Users/canse/Documents/Code/basic-spring/basic-spring/documentation/logging-json.md)

---

## Bonnes pratiques

* garde la console pour les environnements conteneurisés
* évite les fichiers de logs dans Docker / Kubernetes sauf besoin précis
* n’active pas `DEBUG` partout en production
* garde les patterns lisibles et stables
* préfère des champs structurés si la destination finale est ELK

---

## Liens officiels

* Spring Boot Logging : https://docs.spring.io/spring-boot/reference/features/logging.html
* Spring Boot custom log configuration : https://docs.spring.io/spring-boot/reference/features/logging.html#features.logging.custom-log-configuration
* Logback manual : https://logback.qos.ch/manual/index.html
* Logback configuration : https://logback.qos.ch/manual/configuration.html
* Logback appenders : https://logback.qos.ch/manual/appenders.html
* Logback layouts : https://logback.qos.ch/manual/layouts.html
