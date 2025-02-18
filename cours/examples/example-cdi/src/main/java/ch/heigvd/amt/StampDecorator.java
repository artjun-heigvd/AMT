package ch.heigvd.amt;

import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;

@Priority(1)
@Decorator
public class StampDecorator implements Shelf {

    @Inject
    @Delegate
    Shelf delegate;

    @Override
    public Book getBookByTitle(String title) {
        System.out.println("Stamping book " + title + " as borrowed");
        return delegate.getBookByTitle(title);
    }
}
