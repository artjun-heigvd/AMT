package ch.heigvd.amt.services;

import ch.heigvd.amt.entities.Artist;
import ch.heigvd.amt.entities.Artwork;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

@ApplicationScoped
public class SynchronizationService {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void synchronize() {

        entityManager.createNativeQuery("TRUNCATE TABLE artist CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE artwork CASCADE").executeUpdate();

        RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().build();

        // Synchronize the artists
        var artistsCsvUrl = "https://media.githubusercontent.com/media/MuseumofModernArt/collection/master/Artists.csv";
        try (var inputStream = new URL(artistsCsvUrl).openStream();
             var reader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(inputStream))).withCSVParser(rfc4180Parser).build()) {
            reader.skip(1);
            String[] tokens;
            while ((tokens = reader.readNext()) != null) {
                var artist = new Artist();
                artist.setId(Long.parseLong(tokens[0].replace("\"", "")));
                artist.setName(tokens[1].replace("\"", ""));
                artist.setBio(tokens[2].replace("\"", ""));
                artist.setNationality(tokens[3].replace("\"", ""));
                artist.setGender(tokens[4].replace("\"", ""));
                artist.setBirthDate(Integer.parseInt(tokens[5].replace("\"", "")));
                artist.setDeathDate(Integer.parseInt(tokens[6].replace("\"", "")));
                artist.setWikiLink(tokens[7].replace("\"", ""));
                artist.setArtworks(new ArrayList<>());
                entityManager.persist(artist);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        // Synchronize the artworks
        var artworksCsvUrl = "https://media.githubusercontent.com/media/MuseumofModernArt/collection/master/Artworks.csv";
        try (var inputStream = new URL(artworksCsvUrl).openStream();
             var reader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(inputStream))).withCSVParser(rfc4180Parser).build()) {
            reader.skip(1);
            String[] tokens;
            while ((tokens = reader.readNext()) != null) {
                var artwork = new Artwork();
                artwork.setTitle(tokens[0].replace("\"", ""));

                var artists = new ArrayList<Artist>();
                var artistIds = tokens[2].replace("\"", "").replace(" ", "").split(",");
                for (var artistId : artistIds) {
                    if (artistId.isEmpty()) {
                        continue;
                    }
                    artists.add(entityManager.find(Artist.class, Long.parseLong(artistId)));
                }
                artwork.setArtist(artists);

                artwork.setUrl(tokens[18].replace("\"", ""));
                artwork.setThumbnailUrl(tokens[19].replace("\"", ""));
                entityManager.persist(artwork);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

}
