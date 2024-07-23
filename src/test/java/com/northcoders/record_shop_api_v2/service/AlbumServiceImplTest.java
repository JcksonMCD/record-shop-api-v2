package com.northcoders.record_shop_api_v2.service;

import com.northcoders.record_shop_api_v2.dto.AlbumDTO;
import com.northcoders.record_shop_api_v2.dto.ArtistDTO;
import com.northcoders.record_shop_api_v2.model.Album;
import com.northcoders.record_shop_api_v2.model.Artist;
import com.northcoders.record_shop_api_v2.model.Genre;
import com.northcoders.record_shop_api_v2.repository.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumServiceImplTest {

    @Mock
    private AlbumRepository albumRepository;

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
        when(albumRepository.findAll()).thenReturn(List.of(album));
        List<AlbumDTO> expectedDTOList = List.of(albumDTO);

        // Act
        List<AlbumDTO> actualDTOList = albumService.getAllAlbums();

        // Assert
        assertEquals(expectedDTOList, actualDTOList);
    }

    @Test
    @DisplayName("albumService.postAlbum(): Returns album DTO")
    void AlbumService_PostAlbum_ReturnsResponseDTO() {
        // Arrange
        when(albumRepository.save(album)).thenReturn(album);
        AlbumDTO expectedDTO = albumDTO;

        // Act
        AlbumDTO actualDTO = albumService.postAlbum();

        // Assert
        assertEquals(expectedDTO, actualDTO);
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