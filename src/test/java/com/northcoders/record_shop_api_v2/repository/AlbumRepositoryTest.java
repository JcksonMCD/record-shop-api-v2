package com.northcoders.record_shop_api_v2.repository;

import com.northcoders.record_shop_api_v2.model.Album;
import com.northcoders.record_shop_api_v2.model.Genre;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AlbumRepositoryTest {

    @Autowired
    AlbumRepository albumRepository;

    private Album album;
    private Album album2;

    @BeforeEach
    public void init(){
        album = Album.builder().albumName("Nothing But Thieves").genre(Genre.ROCK).releaseYear(2020).build();
        album2 = Album.builder().albumName("Aphex Twin").genre(Genre.ELECTRONIC).releaseYear(1992).build();
    }

    @AfterEach
    public void tearDown() {
        albumRepository.deleteAll();
    }

    @Test
    @DisplayName("albumRepository.save(): Saves album.")
    public void AlbumRepository_save_SavesAlbum(){
        Album savedAlbum = albumRepository.save(album);

        assertNotNull(savedAlbum);
        assertEquals(4, savedAlbum.getId());
        assertEquals("Nothing But Thieves", savedAlbum.getAlbumName());
        assertEquals(Genre.ROCK, savedAlbum.getGenre());
        assertEquals(2020, savedAlbum.getReleaseYear());
    }

    @Test
    @DisplayName("albumRepository.getAll(): Returns all albums.")
    public void AlbumRepository_GetAll_ReturnsAllAlbums(){
        albumRepository.save(album);
        albumRepository.save(album2);

        List<Album> albumList = albumRepository.findAll();
        assertNotNull(albumList);
        assertEquals(albumList.size(), 2);
        assertTrue(albumList.contains(album));
        assertTrue(albumList.contains(album2));
    }

    @Test
    @DisplayName("albumRepository.findById(): Returns album with the corresponding id.")
    public void AlbumRepository_FindById_ReturnsAlbumWithCorrespondingID(){
        Album savedAlbum = albumRepository.save(album);

        Optional<Album> foundAlbumOpt = albumRepository.findById(savedAlbum.getId());

        assertTrue(foundAlbumOpt.isPresent(), "The album should be present");
        Album foundAlbum = foundAlbumOpt.get();
        assertEquals(savedAlbum.getId(), foundAlbum.getId());
        assertEquals("Nothing But Thieves", foundAlbum.getAlbumName());
        assertEquals(Genre.ROCK, foundAlbum.getGenre());
        assertEquals(2020, foundAlbum.getReleaseYear());
    }

    @Test
    @DisplayName("albumRepository.deleteById(): Successfully deletes an album at a given id.")
    public void AlbumRepository_DeleteById_ReturnsAlbumWithCorrespondingID(){
        album = albumRepository.save(album);
        Long albumId = album.getId();
        assertThat(albumRepository.findById(albumId)).isPresent();

        albumRepository.deleteById(albumId);

        assertThat(albumRepository.findById(albumId)).isNotPresent();
    }
}