# Basic Spring

Petit projet Spring Boot servant d'exemple pour récupérer des arguments passés au lancement de l'application.

Il montre deux approches :

- `ApplicationRunner` avec `ApplicationArguments`
- `CommandLineRunner` avec injection de `ApplicationArguments`

## Prérequis

- Java 17
- Maven Wrapper inclus (`./mvnw`)

## Lancer l'application

Depuis la racine du projet :

```bash
./mvnw spring-boot:run
```

## Arguments disponibles

L'application lit actuellement les options suivantes :

- `person.name`
- `file.format`
- `file.path`

Exemples :

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--person.name=Test
```

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--person.name=Test,Demo
```

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--file.format=xlsx,--file.path=/tmp/data.xlsx
```

## Ce que fait le projet

Au démarrage, l'application :

- vérifie si l'option `person.name` est présente
- affiche les valeurs associées à `person.name`
- affiche la propriété Spring `test.value`
- affiche les valeurs de `file.format` et `file.path`

## Exemples de code

### Avec `ApplicationRunner`

Cette approche permet d'accéder directement aux arguments dans la méthode `run(ApplicationArguments args)`.

Méthodes utiles :

- `args.containsOption("person.name")`
- `args.getOptionValues("person.name")`

### Avec `CommandLineRunner`

Un bean `CommandLineRunner` peut recevoir `ApplicationArguments` par injection :

```java
applicationArguments.getOptionValues("file.path");
```

## Configuration

Le fichier de configuration principal est :

- [src/main/resources/application.properties](/Users/canse/Documents/Code/basic-spring/basic-spring/src/main/resources/application.properties)

Propriété actuellement définie :

```properties
test.value=testtest
```

## Structure utile

- [src/main/java/org/canse/basicspring/BasicSpringApplication.java](/Users/canse/Documents/Code/basic-spring/basic-spring/src/main/java/org/canse/basicspring/BasicSpringApplication.java)
- [src/test/java/org/canse/basicspring/BasicSpringApplicationTests.java](/Users/canse/Documents/Code/basic-spring/basic-spring/src/test/java/org/canse/basicspring/BasicSpringApplicationTests.java)

## Documentation

- Spring Boot `ApplicationRunner` : https://docs.spring.io/spring-boot/4.0/api/java/org/springframework/boot/ApplicationRunner.html
- Spring Boot `CommandLineRunner` : https://docs.spring.io/spring-boot/4.0/api/java/org/springframework/boot/CommandLineRunner.html
- `ApplicationArguments` : https://docs.spring.io/spring-boot/4.0/api/java/org/springframework/boot/ApplicationArguments.html
- Plugin Maven Spring Boot : https://docs.spring.io/spring-boot/4.0.5/maven-plugin
# args-deep-dive
# args-deep-dive
