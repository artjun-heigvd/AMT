Hello, vous trouverez ci-dessous un feedback sur votre labo 2.

### Exercice 1

#### Rapport

- Q1 trop light, concrètement à quoi ressemblerait la solution que vous proposez ? Quels sont les pros et les cons ?

### Exercice 2

- La méthode récupère tous les films de l'inventaire du store et 
  effectue ensuite un filtrage en mémoire. Cela peut entraîner des 
  problèmes de performance, surtout si le nombre de films est élevé. Vous 
  devriez faire le triage directement dans la requête avec to_tsvector par
   exemple pour faire une recherche textuelle dans la DB.

### Exercice 3

- il manque un SERIAL (auto-incrément) sur l'ID de la table role
- pas de PK ni de FK sur la table staff_role

#### Rapport

- très bon rapport

### Exercices 4 et 5

- Il n'était pas vraiment nécessaire de créer un Film et l'assigner à l'inventaire, l'id de inventaire suffit

#### Rapport

- Vous pouvez développer un peu plus les désavantages

### Fonctionnement de l'app

### Bonus

Bonus OK
