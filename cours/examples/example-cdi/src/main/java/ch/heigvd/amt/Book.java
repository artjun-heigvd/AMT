package ch.heigvd.amt;

import java.util.Optional;

public class Book {

    private String title;

    private String author;

    private Optional<String> borrower;

    public Book() {

    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.borrower = Optional.empty();
    }

    public Book(String title, String author, String borrower) {
        this.title = title;
        this.author = author;
        this.borrower = Optional.of(borrower);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public Optional<String> getBorrower() {
        return borrower;
    }

    public void setBorrower(Optional<String> borrower) {
        this.borrower = borrower;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String toString() {
        return title + " by " + author;
    }


}
