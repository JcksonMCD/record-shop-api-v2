package com.northcoders.record_shop_api_v2.repository;

import com.northcoders.record_shop_api_v2.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
