package com.northcoders.record_shop_api_v2.controller;

import com.northcoders.record_shop_api_v2.service.AlbumServiceImpl;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AlbumControllerTest {

    @Mock
    AlbumServiceImpl albumService;

    @InjectMocks
    AlbumController albumController;


    @Test
    void getAllAlbums() {
    }
}