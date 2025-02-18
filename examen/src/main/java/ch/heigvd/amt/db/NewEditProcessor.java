package ch.heigvd.amt.db;

import ch.heigvd.amt.wiki.Processor;
import ch.heigvd.amt.wiki.dtos.MediaWikiRecentChange;

public class NewEditProcessor implements Processor {

    @Override
    public void process(MediaWikiRecentChange event) {
        // TODO. Persist the event
        // Hints: focus on mandatory fields
    }
}
