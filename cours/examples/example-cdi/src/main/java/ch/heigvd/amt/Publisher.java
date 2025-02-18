package ch.heigvd.amt;

import jakarta.enterprise.inject.Produces;

import java.util.ArrayList;
import java.util.List;

public class Publisher {

    @Produces
    @HeroicFantasy
    public Book scienceFiction() {
        return new Book("The Lord of the Rings", "J.R.R. Tolkien");
    }

    @Produces
    @ScienceFiction
    public Book heroicFantasy() {
        return new Book("Dune", "Frank Herbert");
    }

    @Produces
    public List<Book> books() {
        List<Book> books = new ArrayList<>();
        books.add(scienceFiction());
        books.add(heroicFantasy());
        return books;
    }

}
