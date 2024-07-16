package com.northcoders.record_shop_api_v2.repository;

import com.northcoders.record_shop_api_v2.model.Album;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {
}
