package ch.heigvd.amt;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

import java.util.Date;
import java.util.Optional;

@Ring
@ApplicationScoped
public class LibrarianImpl implements Librarian {

    @Inject
    Shelf shelf;

    @Inject
    Event<Book> returnEvent;

    @Override
    public Book borrowBook(String borrower, String title) {
        if (borrower == null || borrower.isEmpty()) {
            throw new IllegalArgumentException("Person cannot be null");
        }
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        var book = shelf.getBookByTitle(title);
        System.out.println(borrower + " borrowed " + book);
        book.setBorrower(Optional.of(borrower));
        return book;
    }

    @Override
    public void returnBook(Book book) {
        System.out.println(book.getBorrower().get() + " returned " + book + " on " + new Date());
        returnEvent.fire(book);
    }

}
