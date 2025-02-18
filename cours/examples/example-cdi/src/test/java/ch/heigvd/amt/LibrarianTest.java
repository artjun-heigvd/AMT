package ch.heigvd.amt;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@QuarkusTest
class LibrarianTest {

    @Inject
    Librarian library;

    @Test
    void borrowBook() {
        var borrower = "John Doe";
        library.borrowBook(borrower, "The Lord of the Rings");
    }

    @Test
    void returnBook() {
        var borrower = "John Doe";
        var author = "J.R.R. Tolkien";
        var book = new Book("The Lord of the Rings", author);
        book.setBorrower(Optional.of(borrower));
        library.returnBook(book);
    }
}