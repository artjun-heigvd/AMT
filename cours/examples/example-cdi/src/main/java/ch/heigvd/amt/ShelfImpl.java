package ch.heigvd.amt;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ShelfImpl implements Shelf {

    private List<Book> books;

    @Inject
    @HeroicFantasy
    Book book;

    @Inject
    @ScienceFiction
    Book book2;

    @PostConstruct
    public void fillShelf() {
        this.books = new ArrayList<>();
        this.books.add(book);
        this.books.add(book2);
        System.out.println("BookShelf initialized with " + books.size() + " books");
    }

    @Override
    public Book getBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }

}
