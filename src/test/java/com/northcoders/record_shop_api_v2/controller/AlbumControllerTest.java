package com.northcoders.record_shop_api_v2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.northcoders.record_shop_api_v2.dto.AlbumDTO;
import com.northcoders.record_shop_api_v2.model.Album;
import com.northcoders.record_shop_api_v2.model.Genre;
import com.northcoders.record_shop_api_v2.service.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AlbumController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AlbumControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AlbumService albumService;

    @Autowired
    ObjectMapper objectMapper;

    private Album album;
    private AlbumDTO albumDTO;

    @BeforeEach
    public void init(){
        album = Album.builder().albumName("Nothing But Thieves").genre(Genre.ROCK).releaseYear(2020).build();
        albumDTO = AlbumDTO.builder().albumName("Nothing But Thieves").genre(Genre.ROCK).releaseYear(2020).build();
    }

    @Test
    @DisplayName("GET /album: Returns all albums")
    void AlbumController_GetAll_ReturnsAllAlbumDTOs() throws Exception {
        List<AlbumDTO> responseDTOList = List.of(albumDTO);
        when(albumService.getAllAlbums()).thenReturn(responseDTOList);

        ResultActions response = mockMvc.perform(get("/api/v2/album"));

        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].albumName").value(albumDTO.getAlbumName()))
                .andExpect(jsonPath("$[0].genre").value(albumDTO.getGenre().toString()))
                .andExpect(jsonPath("$[0].releaseYear").value(albumDTO.getReleaseYear()));
    }

    @Test
    @DisplayName("POST /album: Returns album DTO and 'CREATED' status")
    void AlbumController_PostAlbum_ReturnsAlbumDTO() throws Exception {
        when(albumService.postAlbum()).thenReturn(albumDTO);

        ResultActions response = mockMvc.perform(post("/api/v2/album"));

        response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.albumName").value(albumDTO.getAlbumName()))
                .andExpect(jsonPath("$.genre").value(albumDTO.getGenre().toString()))
                .andExpect(jsonPath("$.releaseYear").value(albumDTO.getReleaseYear()));
    }
}