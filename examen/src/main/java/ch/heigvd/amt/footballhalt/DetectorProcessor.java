package ch.heigvd.amt.footballhalt;

import ch.heigvd.amt.wiki.HaltProcessing;
import ch.heigvd.amt.wiki.Processor;
import ch.heigvd.amt.wiki.dtos.MediaWikiRecentChange;

@HaltProcessing
public class DetectorProcessor implements Processor {
    @Override
    public void process(MediaWikiRecentChange event) {
        if (event.title() != null && event.title().toLowerCase().contains("football")) {
            throw new FootballFound("Football was found in title");
        }
    }
}
