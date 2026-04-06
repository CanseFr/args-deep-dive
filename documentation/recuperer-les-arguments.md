# Recuperer les arguments dans Spring Boot

Spring Boot permet de recuperer des arguments de plusieurs manieres. Le bon choix depend surtout du type de besoin :

- lire des arguments de lancement comme `--person.name=Test`
- lire une configuration applicative provenant de plusieurs sources
- executer une logique au demarrage

## 1. `ApplicationArguments`

C'est la solution la plus adaptee pour lire proprement les arguments passes au lancement.

Exemple :

```java
@Component
class MyRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        boolean present = args.containsOption("person.name");
        var values = args.getOptionValues("person.name");
        var nonOptions = args.getNonOptionArgs();
    }
}
```

A choisir quand :

- l'application est lancee en ligne de commande
- il faut lire des options comme `--person.name=Test`
- il faut distinguer options et arguments simples

Pourquoi c'est un bon choix :

- API claire
- plus propre que manipuler `String[] args`
- pratique pour tester la presence d'une option ou lire plusieurs valeurs

## 2. `ApplicationRunner`

`ApplicationRunner` permet d'executer du code au demarrage avec un objet `ApplicationArguments`.

Exemple :

```java
@Component
class StartupRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        System.out.println(args.getOptionValues("person.name"));
    }
}
```

A choisir quand :

- il faut lancer un traitement au demarrage
- ce traitement depend des arguments

Pourquoi le choisir :

- il combine demarrage de l'application et acces propre aux arguments
- c'est souvent le meilleur choix pour une application CLI Spring Boot

## 3. `CommandLineRunner`

`CommandLineRunner` execute aussi du code au demarrage, mais recoit les arguments bruts dans un `String[]`.

Exemple :

```java
@Component
class StartupRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}
```

A choisir quand :

- le besoin est tres simple
- les arguments n'ont pas besoin d'etre parses finement

Limites :

- moins pratique pour lire des options nommees
- le parsing reste a faire manuellement

Dans la plupart des cas, `ApplicationRunner` est preferable si les arguments sont importants.

## 4. Injection directe de `ApplicationArguments`

Il est possible d'injecter `ApplicationArguments` dans un bean sans utiliser directement `ApplicationRunner`.

Exemple :

```java
@Component
class MyService {
    public MyService(ApplicationArguments args) {
        System.out.println(args.getOptionValues("file.path"));
    }
}
```

A choisir quand :

- les arguments doivent etre utilises dans un service
- on veut eviter de concentrer toute la logique dans la classe principale

Pourquoi c'est utile :

- approche plus modulaire
- meilleure separation des responsabilites

## 5. `@Value`

Les arguments de ligne de commande peuvent aussi etre lus comme des proprietes Spring.

Exemple :

```java
@Value("${person.name:defaultValue}")
private String personName;
```

A choisir quand :

- il faut lire une seule valeur simple
- cette valeur peut venir de plusieurs sources comme :
  - la ligne de commande
  - `application.properties`
  - une variable d'environnement

Pourquoi c'est pratique :

- syntaxe courte
- tres bien pour une ou deux proprietes simples

Limites :

- devient vite difficile a maintenir si les proprietes se multiplient
- peu adapte pour une configuration structuree

## 6. `Environment`

Spring permet aussi de lire une propriete dynamiquement via `Environment`.

Exemple :

```java
@Autowired
private Environment environment;

String name = environment.getProperty("person.name");
```

A choisir quand :

- le nom de la propriete est dynamique
- il faut une lecture plus souple qu'avec `@Value`

Pourquoi l'utiliser :

- fonctionne avec toutes les sources de configuration Spring
- utile dans des cas plus dynamiques

Limites :

- plus verbeux
- moins lisible pour un cas simple

## 7. `@ConfigurationProperties`

C'est souvent la meilleure solution pour regrouper plusieurs proprietes liees dans une classe dediee.

Exemple :

```java
@ConfigurationProperties(prefix = "person")
public class PersonProperties {
    private String name;
    private String role;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
```

A choisir quand :

- plusieurs proprietes concernent le meme sujet
- le projet commence a grandir
- il faut une configuration claire et maintenable

Pourquoi c'est souvent le meilleur choix en projet reel :

- code plus propre
- typage fort
- plus facile a tester
- plus simple a faire evoluer

## 8. Lecture directe dans `main(String[] args)`

Les arguments peuvent aussi etre lus directement dans la methode `main`.

Exemple :

```java
public static void main(String[] args) {
    for (String arg : args) {
        System.out.println(arg);
    }
    SpringApplication.run(BasicSpringApplication.class, args);
}
```

A choisir quand :

- presque jamais pour de la logique metier dans Spring Boot

Pourquoi ce n'est pas ideal :

- trop bas niveau
- contourne les abstractions Spring
- moins maintenable

## Quel choix faire

Recommandations pratiques :

- application CLI avec options nommees : `ApplicationArguments`
- traitement au demarrage base sur les arguments : `ApplicationRunner`
- traitement au demarrage tres simple : `CommandLineRunner`
- une propriete isolee : `@Value`
- plusieurs proprietes liees : `@ConfigurationProperties`
- lecture dynamique d'une propriete : `Environment`

## Recommandation pour ce projet

Dans ce projet, le meilleur choix est :

- `ApplicationRunner` pour montrer la logique executee au demarrage
- `ApplicationArguments` pour lire proprement les arguments

Si le projet evolue vers une vraie configuration metier avec plusieurs proprietes, `@ConfigurationProperties` sera plus adapte.

## Documentation officielle

- Spring Boot, demarrage de l'application : https://docs.spring.io/spring-boot/reference/features/spring-application.html
- `ApplicationRunner` : https://docs.spring.io/spring-boot/4.0/api/java/org/springframework/boot/ApplicationRunner.html
- `CommandLineRunner` : https://docs.spring.io/spring-boot/4.0/api/java/org/springframework/boot/CommandLineRunner.html
- `ApplicationArguments` : https://docs.spring.io/spring-boot/4.0/api/java/org/springframework/boot/ApplicationArguments.html
- Configuration externe Spring Boot : https://docs.spring.io/spring-boot/reference/features/external-config.html
