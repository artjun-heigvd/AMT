package ch.heigvd.amt.jpa.resource;

import ch.heigvd.amt.jpa.entity.Customer;
import ch.heigvd.amt.jpa.entity.Film;
import ch.heigvd.amt.jpa.entity.Inventory;
import ch.heigvd.amt.jpa.entity.Staff;
import ch.heigvd.amt.jpa.service.RentalService;
import ch.heigvd.amt.jpa.repository.StaffRepository;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.Blocking;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.resteasy.reactive.RestForm;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

// The existing annotations on this class must not be changed (i.e. new ones are allowed)

/**
 * This class is a JAX-RS resource that provides operations to rent a film.
 * Authors: Rachel Tranchida, Edwin Häffner, Arthur Junod, Eva Ray
 */
@Path("rental")
@Authenticated
public class RentalResource {


  private final RentalService rentalService;

  private final StaffRepository staffRepository;

  /**
   * Constructor for RentalResource.
   *
   * @param rentalService the rental service
   * @param staffRepository the staff repository
   */
  @jakarta.inject.Inject
  public RentalResource(RentalService rentalService, StaffRepository staffRepository) {
    this.rentalService = rentalService;
    this.staffRepository = staffRepository;
  }

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance rental(String username);
    public static native TemplateInstance rental$success(RentalService.RentalDTO rental);
    public static native TemplateInstance rental$failure(String message);
    public static native TemplateInstance searchFilmsResults(
            List<RentalService.FilmInventoryDTO> films);
    public static native TemplateInstance searchFilmsSelect(
            RentalService.FilmInventoryDTO film);
    public static native TemplateInstance searchCustomersResults(
            List<RentalService.CustomerDTO> customers);
    public static native TemplateInstance searchCustomersSelect(
            RentalService.CustomerDTO customer);
  }

  /**
   * Displays the rental page.
   *
   * @param securityContext the security context
   * @return the rental template instance
   */
  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance rental(@Context SecurityContext securityContext) {
    return Templates.rental(securityContext.getUserPrincipal().getName());
  }

  /**
   * Registers a rental.
   *
   * @param securityContext the security context
   * @param inventory the inventory ID
   * @param customer the customer ID
   * @return the template instance indicating success or failure
   */
  @POST
  @Produces(MediaType.TEXT_HTML)
  @Authenticated
  @Blocking
  public TemplateInstance registerRental(@Context SecurityContext securityContext,
                                         @RestForm Integer inventory, @RestForm Integer customer) {
    if (inventory == null || customer == null) {
      return Templates.rental$failure("The submission is not valid, missing inventory or customer");
    }

    RentalService.FilmInventoryDTO invDTO = rentalService.searchFilmInventory(inventory);
    Inventory inv = new Inventory();
    Film film = new Film();
    film.setTitle(invDTO.title());
    film.setDescription(invDTO.description());
    inv.setFilm(film);
    inv.setId(invDTO.inventoryId());

    RentalService.CustomerDTO customerDTO = rentalService.searchCustomer(customer);
    Customer cust = new Customer();
    cust.setLastName(customerDTO.lastName());
    cust.setFirstName(customerDTO.firstName());
    cust.setId(customerDTO.id());

    Staff staff = staffRepository.searchStaff(securityContext.getUserPrincipal().getName());

    Optional<RentalService.RentalDTO> rent = rentalService.rentFilm(
            inv,
            cust,
            staff
    );

    if (rent.isPresent()) {
      return Templates.rental$success(rent.get());
    } else {
      return Templates.rental$failure("The selected item is not available.");
    }
  }

  /**
   * Selects a film by inventory ID.
   *
   * @param inventory the inventory ID
   * @return the template instance for the selected film
   */
  @GET
  @Path("/film/{inventory}")
  @Produces(MediaType.TEXT_HTML)
  @Blocking
  public TemplateInstance selectFilmsGet(Integer inventory) {
    return Templates.searchFilmsSelect(
            rentalService.searchFilmInventory(inventory)
    );
  }

  /**
   * Searches for films based on a query.
   *
   * @param securityContext the security context
   * @param query the search query
   * @return the template instance with search results
   */
  @POST
  @Path("/film/search")
  @Produces(MediaType.TEXT_HTML)
  @Authenticated
  @Blocking
  public TemplateInstance searchFilmsPost(@Context SecurityContext securityContext, @RestForm String query) {
    if(query == null || query.trim().isEmpty()) {
      return Templates.searchFilmsResults(Collections.emptyList());
    }

    return Templates.searchFilmsResults(
        rentalService.searchFilmInventory(
                query,
                staffRepository.searchStaff(securityContext.getUserPrincipal().getName()).getStore()
        )
    );
  }

  /**
   * Searches for customers based on a query.
   *
   * @param securityContext the security context
   * @param query the search query
   * @return the template instance with search results
   */
  @POST
  @Path("/customer/search")
  @Produces(MediaType.TEXT_HTML)
  @Authenticated
  @Blocking
  public TemplateInstance searchCustomersPost(@Context SecurityContext securityContext, @RestForm String query) {
    if(query == null || query.trim().isEmpty()) {
      return Templates.searchCustomersResults(Collections.emptyList());
    }

    return Templates.searchCustomersResults(
            rentalService.searchCustomer(
                    query,
                    staffRepository.searchStaff(securityContext.getUserPrincipal().getName()).getStore()
            )
    );
  }

  /**
   * Selects a customer by customer ID.
   *
   * @param customer the customer ID
   * @return the template instance for the selected customer
   */
  @GET
  @Path("/customer/{customer}")
  @Produces(MediaType.TEXT_HTML)
  @Blocking
  public TemplateInstance selectCustomerGet(Integer customer) {
    return Templates.searchCustomersSelect(
            rentalService.searchCustomer(customer)
    );
  }
}
