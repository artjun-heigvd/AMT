# Cheatsheet
## Serialization
Pour customiser la sérialisation il faut que l'Objet implémente `Externalizable` pour override `writeExternal` et `readExternal` qui auront chacun en paramètre le in/out qui nous permet de définir ce qu'on écrit/lit.
### XML
Parsers:
- DOM bien pour les petit documents
- SAX bien pour les document larges, utilise des callbacks
- StAX bien pour les documents larges, utilises des events

Utiliser JAXB.

## Messaging
Comme extension "quarkus-qpid-jms" et "quarkus-smallrye-reactive-messaging-amqp".

Bien regarder exemples.
## Transaction
Dans les services, les marquer `@Transactional`

[Regarder](https://amt-classroom.github.io/slides/6-transactions-and-validation/index.html#/2/12) les niveaux d'isolation si violation
## Presentation layer
1. Tester avec Resteasy
2. JAX-RS pour RESTful web service
### DTO
1. Pas copie du modèle du domaine
2. Pas de logique business
3. Transférer les données entre clients et serveur
## Dependency injection
### Scope
1. `@ApplicationScoped` une instance par application (lazy)
2. `@SessionScoped` une instance par session (lazy)
3. `@RequestScoped` une instance par requête (lazy)
4. `@Dependent` une instance par point d'injection (défaut) (lazy)
5. `@Singleton` une instance par application mais créée au début et détruite à la fin (eager)
### Lifecycle
1. `@PostContruct`
2. `@PreDestroy`
### Interceptors
Implémentation du logging, sécurité...

Pas de business logic
1. `@InterceptorBinding` crée une annotation pour bind l'interceptor
2. `@Interceptor` définit un intercepteur
3. `@AroundInvoke` méthode appelée avant et après l'exécution d'un autre
4. `@AroundConstruct` méthode appelée avant et après l'instanciation
### Observer
`@Observes` pour définir une méthode observer qui est appelée quand un événement se passe
### Stubbing and mocking
Utiliser Mockito

[Regarder ici](https://amt-classroom.github.io/slides/9-dependency-injection-and-mocking/index.html#/3/2)
## DB Migration
Utiliser quarkus-flyway
## Data
### Entity
Annotations
1. `@Id` pour le champ Id dans la DB
2. `@Column` avec champ `name=String` et potentiellement `nullable=bool`
3. Pour les relations "un à plusieurs" on utilise 2 annotations: 
   1. `@ManyToOne` avec `@JoinColumn` qui a le champ `name=String` avec le nom de la colonne qui est référencée, cette annotation est du côté de l'entité qui est "plusieurs".
   2. `@OneToMany` qui sera du coté de l'entité "un" et qui sera sur une liste d'entité "plusieurs" (on peut d'ailleurs rajouter des fonctions direcrement dans l'entité pour modifier cette liste) avec les champs : 
      - `mappedBy=String` qui a le nom de sa propre entité.
      - `cascade=CascadeType.(Type)` qui définit ce qui se passe aux "plusieurs" quand on change l'entité "un".
      - `orphanRemoval=bool` si on veut activer le fait de pouvoir supprimer ou changer une entité "plusieurs" sans impacter le reste.
4. Pour les relation "plusieurs à plusieurs" on utilise `@ManyToMany` dans les entités des deux relations mais il faut utiliser le champ `mappedBy=String` d'un côté avec son propre nom d'entité et `@JoinTable` de l'autre pour définir la table de jointure qui sera utilisée avec les champs :
   - `name=String` pour le nom de la table, en général "Entité1_Entité2".
   - `joinColumns = @JoinColumn(name=String)` qui permet de définir une des deux colonnes utilisée (dans le "name" mettre le nom de l'id d'une des deux entités)
   - `inverseJoinColumns = @JoinColumn(name=String)` définit l'autre colonne (name = pareil)

Regarder le projet d'AMT pour toutes autres types de relations (`@ManyToOne`, `@ManyToMany`...)

On peut utiliser quarkus-hibernate-validator pour limiter la taille de listes ou autres contraintes.
#### EntityManager
1. `em.persist(Entity)` pour faire persister une entité
2. `em.find(Entity.class, 1L)` pour trouver une entité dans le contexte persistent
3. Pour update on trouve l'entité dans le contexte persistent et on change les champs avec les setters
4. Pour enlever une entity on trouve l'entité et on utiliser `em.remove(Entity)`