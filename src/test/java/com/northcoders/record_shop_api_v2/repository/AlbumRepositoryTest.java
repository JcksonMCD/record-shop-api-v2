package com.northcoders.record_shop_api_v2.repository;

import com.northcoders.record_shop_api_v2.model.Album;
import com.northcoders.record_shop_api_v2.model.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AlbumRepositoryTest {

    @Autowired
    AlbumRepository albumRepository;

    @Test
    @DisplayName("albumRepository.getAll(): Returns all albums.")
    public void AlbumRepository_GetAll_ReturnsAllAlbums(){
        // Arrange
        Album album = Album.builder().albumName("Nothing But Thieves").genre(Genre.ROCK).releaseYear(2020).build();
        Album album2 = Album.builder().albumName("Aphex Twin").genre(Genre.ELECTRONIC).releaseYear(1992).build();
        // Act
        albumRepository.save(album);
        albumRepository.save(album2);

        List<Album> albumList = albumRepository.findAll();
        // Assert
        assertNotNull(albumList);
        assertEquals(albumList.size(), 2);
    }
}