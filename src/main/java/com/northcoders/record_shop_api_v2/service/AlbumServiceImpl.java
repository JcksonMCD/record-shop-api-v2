package com.northcoders.record_shop_api_v2.service;

import com.northcoders.record_shop_api_v2.dto.AlbumDTO;
import com.northcoders.record_shop_api_v2.dto.AlbumGetAllResponse;
import com.northcoders.record_shop_api_v2.dto.ArtistDTO;
import com.northcoders.record_shop_api_v2.exceptions.AlbumNotFoundException;
import com.northcoders.record_shop_api_v2.model.Album;
import com.northcoders.record_shop_api_v2.model.Artist;
import com.northcoders.record_shop_api_v2.repository.AlbumRepository;
import com.northcoders.record_shop_api_v2.repository.ArtistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    public AlbumGetAllResponse getAllAlbums(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Album> albums = albumRepository.findAll(pageable);
        List<Album> albumList = albums.getContent();
        List<AlbumDTO> content = albumList.stream()
                .map(this::mapToDTO)
                .toList();

        AlbumGetAllResponse getAllResponse = new AlbumGetAllResponse();
            getAllResponse.setContent(content);
            getAllResponse.setPageNo(albums.getNumber());
            getAllResponse.setPageSize(albums.getSize());
            getAllResponse.setTotalPages(albums.getTotalPages());
            getAllResponse.setTotalElements(albums.getTotalElements());
            getAllResponse.setLast(albums.isLast());

            return getAllResponse;
    }

    @Override
    @Cacheable(cacheNames = "albums", key = "#id")
    public AlbumDTO getAlbumById(long id) {
        Album foundAlbum = albumRepository.findById(id).orElseThrow(
                () -> new AlbumNotFoundException("No album found at given id."));

        return mapToDTO(foundAlbum);
    }

    @Override
    @CachePut(cacheNames = "albums", key = "#id")
    public AlbumDTO editAlbumById(long id, AlbumDTO albumDTO) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new AlbumNotFoundException("No album found at this id to update."));

        album.setAlbumName(albumDTO.getAlbumName());
        album.setGenre(albumDTO.getGenre());
        album.setArtUrl(albumDTO.getArtUrl());
        album.setReleaseYear(albumDTO.getReleaseYear());
        album.setStockQuantity(albumDTO.getStockQuantity());

        // Check if the artist exists; if not, save the new artist
        Artist artist = artistRepository.findByName(albumDTO.getArtist().getName());
        if (artist == null) {
            artist = artistRepository.save(mapToEntity(albumDTO.getArtist()));
        }
        album.setArtist(artist);

        Album updatedAlbum = albumRepository.save(album);

        return mapToDTO(updatedAlbum);
    }

    @Override
    @CacheEvict(cacheNames = "albums", key = "#id")
    public void deleteAlbumById(long id) {
        Album album = albumRepository.findById(id).orElseThrow(
                () -> new AlbumNotFoundException("No album found at this id so no deletion has taken place.")
        );

        albumRepository.delete(album);
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
