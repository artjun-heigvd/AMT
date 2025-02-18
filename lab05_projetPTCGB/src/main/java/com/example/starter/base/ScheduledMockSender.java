package com.example.starter.base;

import com.example.starter.base.service.RatingService;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class that sends a rating every 2 minutes. This class is mostly made for testing purposes and allows
 * to demonstrate how jms and mailer can be used to send emails.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@ApplicationScoped
public class ScheduledMockSender implements Runnable{

    @Inject
    RatingService ratingService;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private static final Logger LOG = Logger.getLogger(ScheduledMockSender.class);

    /**
     * Listen to the startup event to start the scheduler.
     * @param ev the startup event
     */
    void onStart(@Observes StartupEvent ev){
        LOG.debug("scheduler started");
        scheduler.scheduleWithFixedDelay(this, 0L, 2L, TimeUnit.MINUTES);
    }

    /**
     * Listen to the shutdown event to stop the scheduler.
     * @param ev the shutdown event
     */
    void onStop(@Observes ShutdownEvent ev){
        LOG.debug("scheduler stopped");
        scheduler.shutdown();
    }

    /**
     * Run method that sends a rating every 2 minutes.
     */
    @Override
    public void run() {
        LOG.debug("sending rating");
        ratingService.sendRatingMessage(2, 3, 4, "New rating");
    }
}
