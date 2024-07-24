package com.northcoders.record_shop_api_v2.service;

import com.northcoders.record_shop_api_v2.dto.AlbumDTO;
import com.northcoders.record_shop_api_v2.dto.ArtistDTO;
import com.northcoders.record_shop_api_v2.exceptions.AlbumNotFoundException;
import com.northcoders.record_shop_api_v2.model.Album;
import com.northcoders.record_shop_api_v2.model.Artist;
import com.northcoders.record_shop_api_v2.repository.AlbumRepository;
import com.northcoders.record_shop_api_v2.repository.ArtistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService{
    AlbumRepository albumRepository;
    ArtistRepository artistRepository;

    // Autowired annotation used on constructor vs. directly on the repository declaration for the benefit of unit testing.
    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    @Transactional
    public AlbumDTO postAlbum(AlbumDTO albumDTO) {
        Artist artist = artistRepository.findByName(albumDTO.getArtist().getName());
        // Check if artist exists in the artist repository
        if (artist == null) {
            // Save artist if new and capture the returned entity
            artist = artistRepository.save(mapToEntity(albumDTO.getArtist()));
        }

        Album album = mapToEntity(albumDTO);
        // Set the saved artist entity to the album
        album.setArtist(artist);

        Album savedAlbum = albumRepository.save(album);

        return mapToDTO(savedAlbum);
    }

    @Override
    public List<AlbumDTO> getAllAlbums() {
        List<Album> albums = albumRepository.findAll();
        return albums.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public AlbumDTO getAlbumById(long id) {
        Album foundAlbum = albumRepository.findById(id).orElseThrow(
                () -> new AlbumNotFoundException("No album found at given id."));

        return mapToDTO(foundAlbum);
    }

    @Override
    public AlbumDTO editAlbumById(long id, AlbumDTO albumDTO) {
        return null;
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
