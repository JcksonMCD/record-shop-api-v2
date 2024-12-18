package com.northcoders.record_shop_api_v2.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.northcoders.record_shop_api_v2.model.Artist;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ArtistRepositoryTest {

    @Autowired
    ArtistRepository artistRepository;

    private Artist artist;

    @BeforeEach
    public void init(){
        artist = new Artist(1L ,"Test Artist", null);
    }

    @AfterEach
    public void tearDown() {
        artistRepository.deleteAll();
    }

    @Test
    @DisplayName("artistRepository.save(): Saves artist.")
    public void ArtistRepository_save_SavesArtist(){
        Artist savedArtist = artistRepository.save(artist);

        assertNotNull(savedArtist);
        assertEquals(1, savedArtist.getId());
        assertEquals("Test Artist", savedArtist.getName());
        assertNull(savedArtist.getAlbums());
    }

    @Test
    @DisplayName("artistRepository.findByName(): Finds an artist from the repository by name.")
    public void ArtistRepository_findByName_ReturnsArtistByName(){
        artistRepository.save(artist);

        Artist foundArtist = artistRepository.findByName(artist.getName());

        assertNotNull(foundArtist);
        assertEquals(2, foundArtist.getId());
        assertEquals("Test Artist", foundArtist.getName());
        assertNull(foundArtist.getAlbums());
    }


}