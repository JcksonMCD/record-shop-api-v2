package com.northcoders.record_shop_api_v2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @DisplayName("POST /album: Returns album DTO and 'CREATED' status")
    void AlbumController_PostAlbum_ReturnsAlbumDTO() throws Exception {
        when(albumService.postAlbum(albumDTO)).thenReturn(albumDTO);

        ResultActions response = mockMvc.perform(
                post("/api/v2/album")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(albumDTO))
        );

        response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.albumName").value(albumDTO.getAlbumName()))
                .andExpect(jsonPath("$.genre").value(albumDTO.getGenre().toString()))
                .andExpect(jsonPath("$.releaseYear").value(albumDTO.getReleaseYear()));
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
    @DisplayName("GET /albumById: Returns albumDTO with corresponding id")
    void AlbumController_GetById_ReturnsAlbumDTOWithMatchingID() throws Exception {
        AlbumDTO responseDTO = albumDTO;
        when(albumService.getAlbumById(1)).thenReturn(responseDTO);

        ResultActions response = mockMvc.perform(get("/api/v2/album/1"));

        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.albumName").value(albumDTO.getAlbumName()))
                .andExpect(jsonPath("$.genre").value(albumDTO.getGenre().toString()))
                .andExpect(jsonPath("$.releaseYear").value(albumDTO.getReleaseYear()));
    }

    @Test
    @DisplayName("PUT /album: Returns albumDTO of the up to date version of the album.")
    void AlbumController_EditById_ReturnsAlbumDTO() throws Exception {
        AlbumDTO responseDTO = albumDTO;
        when(albumService.editAlbumById(1, albumDTO)).thenReturn(responseDTO);

        ResultActions response = mockMvc.perform(
                put("/api/v2/album/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(albumDTO))
        );

        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.albumName").value(albumDTO.getAlbumName()))
                .andExpect(jsonPath("$.genre").value(albumDTO.getGenre().toString()))
                .andExpect(jsonPath("$.releaseYear").value(albumDTO.getReleaseYear()));
    }

    @Test
    @DisplayName("DELETE /album: Returns a string stating album was successfully deleted.")
    void AlbumController_DeleteById_ReturnsAlbumDTO() throws Exception {
        doNothing().when(albumService).deleteAlbumById(1L);

        ResultActions response = mockMvc.perform(delete("/api/v2/album/1"));

        response
                .andExpect(status().isOk())
                .andExpect(content().string("Album deleted at id: 1"));

        verify(albumService, times(1)).deleteAlbumById(1L);
    }

    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}