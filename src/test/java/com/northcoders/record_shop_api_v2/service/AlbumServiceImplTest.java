package com.northcoders.record_shop_api_v2.service;

import com.northcoders.record_shop_api_v2.dto.AlbumDTO;
import com.northcoders.record_shop_api_v2.dto.ArtistDTO;
import com.northcoders.record_shop_api_v2.model.Album;
import com.northcoders.record_shop_api_v2.model.Artist;
import com.northcoders.record_shop_api_v2.model.Genre;
import com.northcoders.record_shop_api_v2.repository.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        album = Album.builder().albumName("Nothing But Thieves").artist(new Artist()).genre(Genre.ROCK).releaseYear(2020).build();
        albumDTO = AlbumDTO.builder().albumName("Nothing But Thieves").artist(new ArtistDTO()).genre(Genre.ROCK).releaseYear(2020).build();

    }

    @Test
    void AlbumService_GetAll_ReturnsResponseDTO() {
        when(albumRepository.findAll()).thenReturn(List.of(album));

        List<AlbumDTO> expectedDTOList = List.of(albumDTO);
        List<AlbumDTO> actualDTOList = albumService.getAllAlbums();

        assertEquals(expectedDTOList, actualDTOList);
    }
}