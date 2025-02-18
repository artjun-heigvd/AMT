package ch.heigvd.amt;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class BadMockExample {

    @Inject
    Librarian librarian;

    @BeforeAll
    public static void setup() {
        Librarian mock = Mockito.mock(LibrarianImpl.class);
        Mockito.when(mock.borrowBook("Bertil Chapuis", "Dune")).thenReturn(new Book("Dune", "Frank Herbert"));
        QuarkusMock.installMockForType(mock, Librarian.class);
    }

    @Test
    void test() {
        var book = librarian.borrowBook("Bertil Chapuis", "Dune");
        assertEquals("Dune", book.getTitle());
    }

}