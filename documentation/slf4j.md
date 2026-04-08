# 🧩 1. SLF4J : c’est quoi exactement ?

SLF4J

👉 **SLF4J (Simple Logging Facade for Java)** est une **façade de logging**.

➡️ Ça veut dire :

* ce n’est **pas un logger en soi**
* c’est une **interface** entre ton code et un framework de log

### 🎯 Pourquoi c’est utile ?

Tu écris :

```java
logger.info("User created");
```

Et derrière, tu peux brancher :

* Logback (le plus courant avec Spring Boot)
* Log4j
* ou autre

👉 Donc tu peux changer de système de logs **sans changer ton code**

---

# ⚙️ 2. Lombok : simplifier le code

Project Lombok

Lombok génère du code automatiquement à la compilation.

Exemples classiques :

* `@Getter`, `@Setter`
* `@Builder`
* `@AllArgsConstructor`

👉 Et pour les logs :

### 👉 `@Slf4j`

---

# 🔥 3. @Slf4j : le combo magique

Quand tu ajoutes :

```java
@Slf4j
public class UserService {
}
```

👉 Lombok génère automatiquement :

```java
private static final Logger log = LoggerFactory.getLogger(UserService.class);
```

➡️ Tu peux directement écrire :

```java
log.info("User created");
log.error("Error occurred");
```

---

# 🧠 4. Les niveaux de log

Avec SLF4J :

```java
log.trace("Très détaillé");
log.debug("Debug");
log.info("Info générale");
log.warn("Attention");
log.error("Erreur");
```

### 🪜 Ordre de gravité :

```
TRACE < DEBUG < INFO < WARN < ERROR
```

---

# ⚡ 5. Bonnes pratiques importantes

### ✅ 1. Ne jamais concaténer les strings

❌ Mauvais :

```java
log.info("User id: " + user.getId());
```

✅ Bon :

```java
log.info("User id: {}", user.getId());
```

👉 Pourquoi ?

* évite des calculs inutiles si le log est désactivé
* plus performant

---

### ✅ 2. Logger les exceptions correctement

```java
try {
    ...
} catch (Exception e) {
    log.error("Erreur lors du traitement", e);
}
```

👉 Ne fais pas :

```java
log.error(e.getMessage());
```

---

### ✅ 3. Ne pas surcharger les logs

* `info` → événements métier importants
* `debug` → détails techniques
* `error` → seulement les vraies erreurs

---

# 🧱 6. Exemple concret (Spring Boot)

```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    public void createUser(String name) {
        log.info("Création de l'utilisateur: {}", name);

        try {
            // logique métier
            log.debug("Utilisateur {} en cours de traitement", name);
        } catch (Exception e) {
            log.error("Erreur lors de la création de {}", name, e);
        }
    }
}
```

---

# ⚙️ 7. Configuration simple

Dans `application.yml` :

```yaml
logging:
  level:
    root: INFO
    com.monapp: DEBUG
```

👉 Ça permet de :

* garder du DEBUG pour ton code
* éviter le bruit des libs externes
* définir un niveau de log par package

Pour la configuration détaillée, voir :

* [logging-application-properties-yaml.md](/Users/canse/Documents/Code/basic-spring/basic-spring/documentation/logging-application-properties-yaml.md)
* [logging-logback-xml.md](/Users/canse/Documents/Code/basic-spring/basic-spring/documentation/logging-logback-xml.md)

---

# 🚀 8. Résumé simple

👉 **SLF4J**

* API de logging
* indépendante du moteur

👉 **Lombok @Slf4j**

* évite d’écrire du boilerplate
* injecte automatiquement le logger

👉 Ensemble :
💥 code propre + logs puissants

---

# 💬 Aller plus loin

Pour éviter de mélanger les concepts et la configuration :

* config via `application.properties` ou `application.yml` : [logging-application-properties-yaml.md](/Users/canse/Documents/Code/basic-spring/basic-spring/documentation/logging-application-properties-yaml.md)
* config via `logback-spring.xml` : [logging-logback-xml.md](/Users/canse/Documents/Code/basic-spring/basic-spring/documentation/logging-logback-xml.md)
* logging JSON : [logging-json.md](/Users/canse/Documents/Code/basic-spring/basic-spring/documentation/logging-json.md)
* logs avec Docker / Kubernetes / ELK : [logging-docker-kubernetes-elk.md](/Users/canse/Documents/Code/basic-spring/basic-spring/documentation/logging-docker-kubernetes-elk.md)
