Hello, vous trouverez ci-dessous un feedback sur votre labo 1.

### Exercice 1

- private LocalDate createDate = LocalDate.now() -> unnecessary, 
  can cause conflicts with the default from db (not very critical)
- enum deepCopy shouldn't return an empty array , disassemble shouldn't return null, assemble shouldn't return an empty array

#### Rapport

### Exercice 2

#### 2a

- Utiliser l'enum au lieu d'écrire directement 'PG'
- Criteria : créer une query sur ActorInPGRating.class et non pas Object[].class
- Criteria : stocker les éléments nécessaires dans des variables pour éviter de les recalculer (p.e. cb.count(films.get("id"))

#### 2b

- Criteria : créer une query sur CountryRentals.class et non pas Object[].class
- Criteria : stocker les éléments nécessaires dans des variables pour éviter de les recalculer (p.e. cb.count(films.get("id"))

#### Rapport

- JPQL pas compatible avec toutes les fonctionnalités de SQL
- un peu light
