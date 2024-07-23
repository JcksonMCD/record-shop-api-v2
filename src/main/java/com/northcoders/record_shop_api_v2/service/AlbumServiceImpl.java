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
import java.util.stream.Collectors;

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
        return albums.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    AlbumDTO mapToDTO(Album album){
        AlbumDTO albumDTO = new AlbumDTO();

        albumDTO.setId(album.getId());
        albumDTO.setAlbumName(album.getAlbumName());
        albumDTO.setGenre(album.getGenre());
        albumDTO.setArtist(mapToDTO(album.getArtist()));
        albumDTO.setArtUrl(album.getArtUrl());
        albumDTO.setReleaseYear(album.getReleaseYear());
        albumDTO.setStockQuantity(album.getStockQuantity());

        return albumDTO;
    }

    ArtistDTO mapToDTO(Artist artist){
        ArtistDTO artistDTO = new ArtistDTO();

        artistDTO.setId(artist.getId());
        artistDTO.setName(artist.getName());

        return  artistDTO;
    }

    Album mapToEntity(AlbumDTO albumDTO){
        Album album = new Album();

        album.setId(albumDTO.getId());
        album.setAlbumName(albumDTO.getAlbumName());
        album.setArtist(mapToEntity(albumDTO.getArtist()));
        album.setGenre(albumDTO.getGenre());
        album.setArtUrl(albumDTO.getArtUrl());
        album.setStockQuantity(albumDTO.getStockQuantity());
        album.setReleaseYear(albumDTO.getReleaseYear());

        return album;
    }

    Artist mapToEntity(ArtistDTO artistDTO){
        Artist artist = new Artist();

        artist.setId(artistDTO.getId());
        artist.setName(artistDTO.getName());

        return artist;
    }
}
