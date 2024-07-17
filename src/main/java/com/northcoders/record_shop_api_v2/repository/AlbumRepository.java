package com.northcoders.record_shop_api_v2.repository;

import com.northcoders.record_shop_api_v2.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
//JPA used over CRUD to offer scalability as offers simple CRUD operations plus pagination, sorting, batch processing and flushing.
public interface AlbumRepository extends JpaRepository<Album, Long> {
}
