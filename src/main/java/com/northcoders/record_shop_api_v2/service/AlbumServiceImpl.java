package com.northcoders.record_shop_api_v2.service;

import com.northcoders.record_shop_api_v2.model.Album;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService{
    @Override
    public List<Album> getAllAlbums() {
        return List.of();
    }
}
