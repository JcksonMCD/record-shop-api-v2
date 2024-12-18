package com.northcoders.record_shop_api_v2.service;

import com.northcoders.record_shop_api_v2.dto.AlbumDTO;
import com.northcoders.record_shop_api_v2.dto.AlbumGetAllResponse;
import com.northcoders.record_shop_api_v2.dto.ArtistDTO;
import com.northcoders.record_shop_api_v2.model.Album;
import com.northcoders.record_shop_api_v2.model.Artist;
import com.northcoders.record_shop_api_v2.model.Genre;
import com.northcoders.record_shop_api_v2.repository.AlbumRepository;
import com.northcoders.record_shop_api_v2.repository.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlbumServiceImplTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private AlbumServiceImpl albumService;

    private Album album;
    private AlbumDTO albumDTO;
    private Artist artist;
    private ArtistDTO artistDTO;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        artist = new Artist();
        artist.setId(1L);
        artist.setName("Test Artist");

        artistDTO =new ArtistDTO();
        artistDTO.setId(1L);
        artistDTO.setName("Test Artist");

        album = Album.builder()
                .id(1L)
                .albumName("Test Album")
                .artist(artist)
                .genre(Genre.ROCK)
                .artUrl("http://example.com/art.jpg")
                .releaseYear(2021)
                .stockQuantity(10)
                .build();

        albumDTO = AlbumDTO.builder()
                .id(1L)
                .albumName("Test Album")
                .artist(artistDTO)
                .genre(Genre.ROCK)
                .artUrl("http://example.com/art.jpg")
                .releaseYear(2021)
                .stockQuantity(10)
                .build();
    }

    @Test
    @DisplayName("albumService.getAll(): Returns all albums as DTOs")
    void AlbumService_GetAll_ReturnsResponseDTO() {
        // Arrange
        Album album1 = new Album(1L, "Album1", new Artist(1L, "Artist1", null), Genre.ROCK, "artURL", 2021, 10);
        Album album2 = new Album(2L, "Album2", new Artist(2L, "Artist2", null), Genre.POP, "artURL", 2021, 10);

        List<Album> albumList = Arrays.asList(album1, album2);
        Page<Album> albumPage = new PageImpl<>(albumList, PageRequest.of(0, 2), albumList.size());

        when(albumRepository.findAll(PageRequest.of(0, 2))).thenReturn(albumPage);

        // Act
        AlbumGetAllResponse response = albumService.getAllAlbums(0, 2);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getContent().size());
        assertEquals(0, response.getPageNo());
        assertEquals(2, response.getPageSize());
        assertEquals(1, response.getTotalPages());
        assertEquals(2, response.getTotalElements());
        assertTrue(response.isLast());
        assertEquals("Album1", response.getContent().get(0).getAlbumName());
        assertEquals("Album2", response.getContent().get(1).getAlbumName());
    }

    @Test
    @Transactional
    @DisplayName("albumService.postAlbum(): Posts album when artist already exists and returns albumDTO.")
    public void postAlbum_ShouldSaveAlbum_WhenArtistExists() {
        // Arrange
        when(artistRepository.findByName("Test Artist")).thenReturn(artist);
        when(albumRepository.save(album)).thenReturn(album);

        // Act
        AlbumDTO savedAlbumDTO = albumService.postAlbum(albumDTO);

        // Assert
        assertNotNull(savedAlbumDTO);
        assertEquals("Test Album", savedAlbumDTO.getAlbumName());
        assertEquals(2021, savedAlbumDTO.getReleaseYear());
        assertEquals(Genre.ROCK, savedAlbumDTO.getGenre());
        assertEquals("Test Artist", savedAlbumDTO.getArtist().getName());

        verify(artistRepository, times(1)).findByName("Test Artist");
        verify(artistRepository, never()).save(artist);
        verify(albumRepository, times(1)).save(album);
    }

    @Test
    @Transactional
    @DisplayName("albumService.postAlbum(): Posts album and artist when artist does not exist in db already and returns albumDTO.")
    public void postAlbum_ShouldSaveAlbumAndNewArtist_WhenArtistDoesNotExist() {
        // Arrange
        when(artistRepository.findByName("Test Artist")).thenReturn(null);
        when(artistRepository.save(artist)).thenReturn(artist);
        when(albumRepository.save(album)).thenReturn(album);

        // Act
        AlbumDTO savedAlbumDTO = albumService.postAlbum(albumDTO);

        // Assert
        assertNotNull(savedAlbumDTO);
        assertEquals("Test Album", savedAlbumDTO.getAlbumName());
        assertEquals(2021, savedAlbumDTO.getReleaseYear());
        assertEquals(Genre.ROCK, savedAlbumDTO.getGenre());
        assertEquals("Test Artist", savedAlbumDTO.getArtist().getName());

        verify(artistRepository, times(1)).findByName("Test Artist");
        verify(artistRepository, times(1)).save(artist);
        verify(albumRepository, times(1)).save(album);
    }

    @Test
    @DisplayName("albumService.getAlbumById(): Returns album DTO corresponding to the album id.")
    public void AlbumService_findByID_ShouldReturnAlbumDTO() {
        // Arrange
        when(albumRepository.findById(1L)).thenReturn(Optional.ofNullable(album));

        // Act
        AlbumDTO foundAlbumDTO = albumService.getAlbumById(1L);

        // Assert
        assertNotNull(foundAlbumDTO);
        assertEquals(1L, foundAlbumDTO.getId());
        assertEquals("Test Album", foundAlbumDTO.getAlbumName());
        assertEquals(2021, foundAlbumDTO.getReleaseYear());
        assertEquals(Genre.ROCK, foundAlbumDTO.getGenre());
        assertEquals("Test Artist", foundAlbumDTO.getArtist().getName());

        verify(albumRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("albumService.getAlbumById(): Returns album DTO corresponding to the album id.")
    public void AlbumService_editByID_ShouldReturnAlbumDTO() {
        // Arrange
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));
        when(albumRepository.save(any(Album.class))).thenReturn(album); // Return the updated album
        when(artistRepository.findByName("Test Artist")).thenReturn(artist);

        // Act
        AlbumDTO editedAlbumDTO = albumService.editAlbumById(1L, albumDTO);

        // Assert
        assertNotNull(editedAlbumDTO);
        assertEquals(1L, editedAlbumDTO.getId());
        assertEquals("Test Album", editedAlbumDTO.getAlbumName());
        assertEquals(2021, editedAlbumDTO.getReleaseYear());
        assertEquals(Genre.ROCK, editedAlbumDTO.getGenre());
        assertEquals("Test Artist", editedAlbumDTO.getArtist().getName());

        verify(albumRepository, times(1)).findById(1L);
        verify(albumRepository, times(1)).save(any(Album.class));
        verify(artistRepository, times(1)).findByName("Test Artist");
    }

    @Test
    @DisplayName("albumService.deleteById(): Calls album repositories delete method.")
    public void AlbumService_deleteByID_ShouldReturnAlbumDTO() {
        // Arrange
        Album album = new Album();
        album.setId(1L);
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));

        // Act
        albumService.deleteAlbumById(1L);

        // Assert
        verify(albumRepository, times(1)).findById(1L);
        verify(albumRepository, times(1)).delete(album);
    }


    @Test
    @DisplayName("albumService.mapToDTO(Album): Returns album as a DTO")
    public void AlbumService_MapToDTO_ReturnsMappedDTOAlbum() {
        // Act
        AlbumDTO responseDTO = albumService.mapToDTO(album);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(album.getId(), responseDTO.getId());
        assertEquals(album.getAlbumName(), responseDTO.getAlbumName());
        assertEquals(album.getGenre(), responseDTO.getGenre());
        assertNotNull(responseDTO.getArtist());
        assertEquals(album.getArtist().getId(), responseDTO.getArtist().getId());
        assertEquals(album.getArtUrl(), responseDTO.getArtUrl());
        assertEquals(album.getReleaseYear(), responseDTO.getReleaseYear());
        assertEquals(album.getStockQuantity(), responseDTO.getStockQuantity());
    }

    @Test
    @DisplayName("albumService.mapToDTO(Artist): Returns artist as a DTO")
    public void AlbumService_MapToDTO_ReturnsMappedDTOArtist() {
        // Act
        ArtistDTO responseDTO = albumService.mapToDTO(artist);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(artist.getId(), responseDTO.getId());
        assertEquals(artist.getName(), responseDTO.getName());
    }

    @Test
    @DisplayName("albumService.mapToEntity(AlbumDTO): Returns albumDTO as an album")
    public void AlbumService_MapToEntity_ReturnsMappedAlbum() {
        // Act
        Album responseEntity = albumService.mapToEntity(albumDTO);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(albumDTO.getId(), responseEntity.getId());
        assertEquals(albumDTO.getAlbumName(), responseEntity.getAlbumName());
        assertEquals(albumDTO.getGenre(), responseEntity.getGenre());
        assertNotNull(responseEntity.getArtist());
        assertEquals(albumDTO.getArtist().getId(), responseEntity.getArtist().getId());
        assertEquals(albumDTO.getArtUrl(), responseEntity.getArtUrl());
        assertEquals(albumDTO.getReleaseYear(), responseEntity.getReleaseYear());
        assertEquals(albumDTO.getStockQuantity(), responseEntity.getStockQuantity());
    }

    @Test
    @DisplayName("albumService.mapToEntity(ArtistDTO): Returns artistDTO as an artist")
    public void AlbumService_MapToEntity_ReturnsMappedDTOArtist() {
        // Act
        Artist responseEntity = albumService.mapToEntity(artistDTO);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(artistDTO.getId(), responseEntity.getId());
        assertEquals(artistDTO.getName(), responseEntity.getName());
    }
}