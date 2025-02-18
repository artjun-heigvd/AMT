package ch.heigvd.amt;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;

@Singleton
public class Manager {

    public void onReturn(@Observes Book book) {
        System.out.println("Book " + book + " is available again");
    }

}
