package com.northcoders.record_shop_api_v2.controller;

import com.northcoders.record_shop_api_v2.dto.AlbumDTO;
import com.northcoders.record_shop_api_v2.model.Album;
import com.northcoders.record_shop_api_v2.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/album")
public class AlbumController {

    AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping
    public ResponseEntity<AlbumDTO> postAlbum(
            @RequestBody AlbumDTO albumDTO
    ){
        return new ResponseEntity<>(albumService.postAlbum(albumDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AlbumDTO>> getAllAlbums(){
        return new ResponseEntity<>(albumService.getAllAlbums(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> getAlbumById(
            @PathVariable long id
    ){
        return new ResponseEntity<>(albumService.getAlbumById(id), HttpStatus.OK);
    }

}
