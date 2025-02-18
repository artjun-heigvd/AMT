package ch.amt;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;

@ApplicationScoped
public class PersonService {

    @Inject
    EntityManager em;

    @PostConstruct
    public void init() {
        this.createAPerson("John", "Doe");
    }

    @Transactional
    public void createAPerson(String  first, String last) {
        var p = new Person();
        p.setFirstName(first);
        p.setLastName(last);
        em.persist(p);
    }

    @Transactional
    public Person createRandom() {
        var p = new Person();
        p.setFirstName(RandomStringUtils.insecure().nextAlphabetic(20));
        p.setLastName(RandomStringUtils.insecure().nextAlphabetic(20));
        em.persist(p);
        return p;
    }

    public String getPerson(Long id) {
       var p = em.find(Person.class, id);
       return p.toString();
    }
}
