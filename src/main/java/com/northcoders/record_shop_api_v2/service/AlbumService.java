package com.northcoders.record_shop_api_v2.service;

import com.northcoders.record_shop_api_v2.dto.AlbumDTO;
import com.northcoders.record_shop_api_v2.model.Album;

import java.util.List;

public interface AlbumService {

    List<AlbumDTO> getAllAlbums(int pageNo, int pageSize);

    AlbumDTO postAlbum(AlbumDTO albumDTO);

    AlbumDTO getAlbumById(long id);

    AlbumDTO editAlbumById(long id, AlbumDTO albumDTO);
}
