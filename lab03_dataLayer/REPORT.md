# Report

##### authors: Edwin Häffner, Arthur Junod, Eva Ray, Rachel Tranchida

## Exercise 1 - Entity mappings

**Report** The tests provided that validate updating entities are using `EntityManager.flush()` and
`EntityManager.clear()`) (e.g. `ActorRepositoryTest.testUpdateActor`).

* Describe precisely from the perspective of SQL statements sent to the database, the difference between:
    * `ActorRepositoryTest.testUpdateActor`
    * `ActorRepositoryTest.testUpdateActorWithoutFlushAndClear`
    * `ActorRepositoryTest.testUpdateActorWithoutClear`

* Explain the behavior differences and why it occurs.

Hints: run the tests using the debugger, look at the SQL statements in the log.

### `ActorRepositoryTest.testUpdateActor`

Here are the SQL statements in the logs :

```postgresql
[Hibernate]
insert
into actor
    (first_name, last_name)
values (?, ?)
returning actor_id
    [Hibernate]
    update
    actor 
set
    first_name=?,
    last_name = ?
where
        actor_id=?

[Hibernate]
select a1_0.actor_id,
       a1_0.first_name,
       a1_0.last_name
from actor a1_0
where a1_0.actor_id = ?
```

This test executes three SQL statements :

- INSERT: Adds a new actor to the database.
- UPDATE: Modifies the newly added actor's first and last names.
- SELECT: Retrieves the updated actor to verify the changes.

`flush()` forces Hibernate to synchronize the persistence context with the database, executing any pending SQL
statements
`clear()` empties the persistence context, meaning any subsequent operations will need to reload the entity from the
database

### `ActorRepositoryTest.testUpdateActorWithoutClear`

SQL queries in the logs :

```postgresql!
[Hibernate] 
    insert 
    into
        actor
        (first_name, last_name) 
    values
        (?, ?) 
    returning actor_id
    
[Hibernate] 
    update
        actor 
    set
        first_name=?,
        last_name=? 
    where
        actor_id=?
```

We can notice that we don't execute the `SELECT` query present in the `testUpdateActor` when we don't execute the
function `clear()` on the entity manager. When we don't execute this function, we don't clear our persistent context and
that is why we do not see a select query as it is meant to retrieve the new data updated in the database to fill our
newly cleared context.

### `ActorRepositoryTest.testUpdateActorWithoutFlushAndClear`

Result in the console :
**`- testUpdateActorWithoutFlushAndClear()`**

```postgresql!
[Hibernate] 
    insert 
    into
        actor
        (first_name, last_name) 
    values
        (?, ?) 
    returning actor_id
```

As we can see we're only doing the `Insert Into` without doing the other functions that the previous calls were doing.
It means that we're only doing the changes in Hibernate's persistence context, but the changes have not been
synchronized with the database yet.

From looking online the flush could still be automatically done by hibernate, before a query as an example, but there's
other actions that will call for an automatic flush.

## Exercise 2 - Querying

**Report** on the query language that you prefer and why.

The language I prefer is raw SQL. It was the easiest and quickest option for me, because I am used to write SQL
statements so I didn't face any major issues. However, raw SQL has its limitations. It’s closely tied to the database,
potentially creating dependency issues, and it can be not easily maintainable and portable.

On the other hand, JPQL offers greater database independence and aligns well with object-oriented principles, thanks to
the use of entities. It also introduce a small learning curve, due to the fact that one has to get familiar with
the JPQL syntax

The Criteria API is more verbose and complex but provides great type safety and dynamic query flexibility, making it
advantageous for complex query scenarios. It is very similar to writing Java code, which can be an advantage for people
who dislike writing SQL queries and make the queries easily reusable.

The Criteria API with metamodel builds on the classic Criteria API datamodel by enhancing type safety and refactoring
ease, though it involves additional setup and complexity.

In conclusion, although SQL remains my preferred query language due to its straightforward syntax and my familiarity
with it, it may not always be the optimal choice in every scenario. The other three options — JPQL, Criteria API, and
Criteria API with metamodel — offer clear advantages in terms of database independence and flexibility, allowing for
easier maintenance and adaptability in diverse environments. Each method has strengths that can make it a better fit for
specific situations, so considering these alternatives can be valuable regarding the project requirements.




