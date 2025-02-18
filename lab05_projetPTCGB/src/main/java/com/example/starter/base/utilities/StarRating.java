package com.example.starter.base.utilities;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

/**
 * StarRating is a class that extends HorizontalLayout.
 * It displays a star rating system.
 * The user can click on the stars to rate something.
 * The user can hover over the stars to see the rating they would give.
 * The user can see the rating they gave.
 * The user can see the rating out of 5
 * The user can see the rating out of 5 in a label.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 *
 */

public class StarRating extends HorizontalLayout {
    private static final int MAX_STARS = 5;
    private Double currentRating = 0.0;
    private boolean readOnly = false;
    private final Button[] stars = new Button[MAX_STARS];
    private final Span ratingLabel;

    /**
     * Constructor for StarRating.
     */
    public StarRating() {
        setSpacing(true);
        setAlignItems(Alignment.CENTER);

        // Create stars
        for (int i = 0; i < MAX_STARS; i++) {
            final int starValue = i + 1;
            stars[i] = createStarButton(starValue);
            add(stars[i]);
        }

        // Add rating label
        ratingLabel = new Span();
        ratingLabel.getStyle()
                .set("margin-left", "1em")
                .set("font-size", "var(--lumo-font-size-s)");
        add(ratingLabel);

        updateStars(0);
    }

    /**
     * Creates a star button.
     *
     * @param value Integer object.
     * @return Button object.
     */
    private Button createStarButton(int value) {
        Button star = new Button(new Icon(VaadinIcon.STAR));
        star.addClassName("star-button");

        // Style the button to look like just an icon
        star.getStyle()
                .setBackground("none")
                .setBorder("none")
                .setPadding("0")
                .setColor("var(--lumo-contrast-50pct)");

        star.addClickListener(e -> {
            if (!readOnly) {
                setRating((double) value);
            }
        });

        // Add hover effects
        star.getElement().addEventListener("mouseover", e -> {
            if (!readOnly) {
                updateStars(value);
            }
        });

        star.getElement().addEventListener("mouseout", e -> {
            if (!readOnly) {
                updateStars(currentRating);
            }
        });

        return star;
    }

    /**
     * Updates the stars.
     *
     * @param rating Double object.
     */
    private void updateStars(double rating) {
        for (int i = 0; i < MAX_STARS; i++) {
            if (i < Math.floor(rating)) {
                // Full star
                stars[i].getStyle().setColor("var(--lumo-primary-color)");
            } else if (i < rating) {
                // Half star (when rating has .5)
                stars[i].getStyle().setColor("var(--lumo-primary-color-50pct)");
            } else {
                // Empty star
                stars[i].getStyle().setColor("var(--lumo-contrast-50pct)");
            }
        }

        // Update rating label
        ratingLabel.setText(String.format("%.1f / 5.0", rating));
    }

    /**
     * Removes the text.
     */
    public void removeText() {
        remove(ratingLabel);
    }

    /**
     * Sets the rating.
     *
     * @param rating Double object.
     */
    public void setRating(Double rating) {
        setRating(rating, false);
    }

    /**
     * Sets the rating.
     *
     * @param rating Double object.
     * @param force  Boolean object.
     */
    public void setRating(Double rating, boolean force) {
        if (rating == null) {
            currentRating = 0.0; // Reset internal state
            updateStars(0);
            ratingLabel.setText("Not yet rated");
        } else if (rating >= 0 && rating <= MAX_STARS && (!readOnly || force)) {
            currentRating = rating;
            updateStars(rating);
            fireEvent(new RatingChangeEvent(this, rating));
        }
    }


    /**
     * Gets the rating.
     *
     * @return Double object.
     */
    public Double getRating() {
        return currentRating;
    }

    /**
     * Sets the read only status.
     *
     * @param readOnly Boolean object.
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        for (Button star : stars) {
            star.setEnabled(!readOnly);
        }
    }

    /**
     * Adds a rating change listener.
     *
     * @param listener RatingChangeListener object.
     * @return Registration object.
     */
    public Registration addRatingChangeListener(RatingChangeListener listener) {
        return addListener(RatingChangeEvent.class, event ->
                listener.ratingChanged(event.getRating()));
    }

    /**
     * RatingChangeListener is a functional interface.
     */
    @FunctionalInterface
    public interface RatingChangeListener {
        void ratingChanged(double newRating);
    }

    /**
     * RatingChangeEvent is a class that extends ComponentEvent.
     */
    public static class RatingChangeEvent extends ComponentEvent<StarRating> {
        private final double rating;

        /**
         * Constructor for RatingChangeEvent.
         *
         * @param source StarRating object.
         * @param rating Double object.
         */
        public RatingChangeEvent(StarRating source, double rating) {
            super(source, false);
            this.rating = rating;
        }

        /**
         * Gets the rating.
         *
         * @return Double object.
         */
        public double getRating() {
            return rating;
        }
    }
}