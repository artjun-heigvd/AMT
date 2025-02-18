package ch.heigvd.amt;

import jakarta.enterprise.context.ApplicationScoped;

public interface Librarian {

    @Ring
    Book borrowBook(String borrower, String title);

    void returnBook(Book book);

}
