package com.northcoders.record_shop_api_v2.repository;

import com.northcoders.record_shop_api_v2.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByName(String name);
}
