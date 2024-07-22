package com.northcoders.record_shop_api_v2.service;

import com.northcoders.record_shop_api_v2.dto.AlbumDTO;
import com.northcoders.record_shop_api_v2.dto.ArtistDTO;
import com.northcoders.record_shop_api_v2.model.Album;
import com.northcoders.record_shop_api_v2.model.Artist;
import com.northcoders.record_shop_api_v2.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService{
    AlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public List<AlbumDTO> getAllAlbums() {
        List<Album> albums = albumRepository.findAll();
        return albums.stream().map();
    }

    private AlbumDTO mapToDTO(Album album){
        AlbumDTO albumDTO = new AlbumDTO();

        albumDTO.setId(album.getId());
        albumDTO.setAlbumName(album.getAlbumName());
        albumDTO.setGenre(album.getGenre());
        // Artist in albumDTO needs setting
        albumDTO.setArtUrl(album.getArtUrl());
        albumDTO.setReleaseYear(album.getReleaseYear());
        albumDTO.setStockQuantity(album.getStockQuantity());

        return albumDTO;
    }

    private ArtistDTO mapToDTO(Artist artist){
        
    }
}
