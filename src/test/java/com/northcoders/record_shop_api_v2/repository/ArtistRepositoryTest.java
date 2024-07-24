package com.northcoders.record_shop_api_v2.repository;

import com.northcoders.record_shop_api_v2.model.Album;
import com.northcoders.record_shop_api_v2.model.Genre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

}