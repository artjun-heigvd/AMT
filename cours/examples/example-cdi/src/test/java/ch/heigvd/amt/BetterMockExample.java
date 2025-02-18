package ch.heigvd.amt;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class BetterMockExample {

    @Inject
    Librarian librarian;

    @BeforeAll
    public static void setup() {
        Shelf mock = Mockito.mock(ShelfImpl.class);
        Mockito.when(mock.getBookByTitle("Dune")).thenReturn(new Book("Dune", "Frank Herbert"));
        QuarkusMock.installMockForType(mock, Shelf.class);
    }

    @Test
    void test() {
        var book = librarian.borrowBook("Bertil Chapuis", "Dune");
        assertEquals("Dune", book.getTitle());
        assertEquals("Bertil Chapuis", book.getBorrower().get());
    }

    @Test
    void testNullTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            librarian.borrowBook("Bertil Chapuis", null);
        });
    }

    @Test
    void testNullBorrower() {
        assertThrows(IllegalArgumentException.class, () -> {
            librarian.borrowBook(null, "Dune");
        });
    }

}