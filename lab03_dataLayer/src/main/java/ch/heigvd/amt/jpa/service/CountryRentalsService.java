package ch.heigvd.amt.jpa.service;

import ch.heigvd.amt.jpa.entity.Rental;
import ch.heigvd.amt.jpa.entity.Country_;
import ch.heigvd.amt.jpa.entity.Rental_;
import ch.heigvd.amt.jpa.entity.Customer_;
import ch.heigvd.amt.jpa.entity.Address_;
import ch.heigvd.amt.jpa.entity.City_;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Service to get the number of rentals per country. The query wanted is the following:
 * The countries with their number of rentals ordered by: their number of rentals in descending order, the country's
 * name and the country unique ID.
 * <p>
 * The service provides 4 methods to achieve the same result:
 * - countryRentals_NativeSQL: using a native SQL query
 * - countryRentals_JPQL: using JPQL
 * - countryRentals_CriteriaString: using the Criteria API with string-based metamodel
 * - countryRentals_CriteriaMetaModel: using the Criteria API with metamodel
 */
@ApplicationScoped
public class CountryRentalsService {

    @Inject
    private EntityManager em;

    /**
     * Record to represent a country with its number of rentals.
     *
     * @param country name of the country
     * @param rentals number of rentals in the country
     */
    public record CountryRentals(String country, Long rentals) {
    }

    /**
     * Get the number of rentals per country using a native SQL query.
     *
     * @return list of countries with their number of rentals
     */
    public List<CountryRentals> countryRentals_NativeSQL() {
        String sql = """
                            SELECT c.country, COUNT(r.rental_id) AS rentals_count
                            FROM country c
                                     JOIN city ci ON c.country_id = ci.country_id
                                     JOIN address a ON ci.city_id = a.city_id
                                     JOIN customer cu ON a.address_id = cu.address_id
                                     JOIN rental r ON cu.customer_id = r.customer_id
                            GROUP BY c.country_id, c.country
                            ORDER BY rentals_count DESC, c.country, c.country_id
                """;

        List<Object[]> results = em.createNativeQuery(sql).getResultList();
        return results.stream()
                .map(row -> new CountryRentals((String) row[0], ((Number) row[1]).longValue()))
                .toList();
    }

    /**
     * Get the number of rentals per country using JPQL.
     *
     * @return list of countries with their number of rentals
     */
    public List<CountryRentals> countryRentals_JPQL() {
        TypedQuery<Object[]> query = em.createQuery("""
                    SELECT country.countryName, COUNT(rental.id)
                    FROM rental rental
                             JOIN rental.customer customer
                             JOIN customer.address address
                             JOIN address.city city
                             JOIN city.country country
                    GROUP BY country.countryName
                    ORDER BY COUNT(rental.id) DESC, country.countryName
                """, Object[].class);

        return query.getResultList().stream()
                .map(row -> new CountryRentals((String) row[0], ((Number) row[1]).longValue()))
                .toList();
    }

    /**
     * Get the number of rentals per country using the Criteria API with string-based metamodel.
     *
     * @return list of countries with their number of rentals
     */
    public List<CountryRentals> countryRentals_CriteriaString() {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Object[].class);
        var rental = query.from(Rental.class);
        var customer = rental.join("customer");
        var address = customer.join("address");
        var city = address.join("city");
        var country = city.join("country");
        query.multiselect(country.get("countryName"), cb.count(rental));
        query.groupBy(country.get("countryName"));
        query.orderBy(cb.desc(cb.count(rental)), cb.asc(country.get("countryName")));

        return em.createQuery(query).getResultList().stream()
                .map(row -> new CountryRentals((String) row[0], ((Number) row[1]).longValue()))
                .toList();
    }

    /**
     * Get the number of rentals per country using the Criteria API with metamodel.
     *
     * @return list of countries with their number of rentals
     */
    public List<CountryRentals> countryRentals_CriteriaMetaModel() {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Object[].class);
        var rental = query.from(Rental.class);
        var customer = rental.join(Rental_.customer);
        var address = customer.join(Customer_.address);
        var city = address.join(Address_.city);
        var country = city.join(City_.country);
        query.multiselect(country.get(Country_.countryName), cb.count(rental));
        query.groupBy(country.get(Country_.countryName));
        query.orderBy(cb.desc(cb.count(rental)), cb.asc(country.get(Country_.countryName)));

        return em.createQuery(query).getResultList().stream()
                .map(row -> new CountryRentals((String) row[0], ((Number) row[1]).longValue()))
                .toList();
    }
}
