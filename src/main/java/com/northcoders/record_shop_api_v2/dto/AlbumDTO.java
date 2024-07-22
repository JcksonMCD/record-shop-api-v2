package com.northcoders.record_shop_api_v2.dto;

import com.northcoders.record_shop_api_v2.model.Genre;
import lombok.Data;

@Data
public class AlbumDTO {
    private long id;
    private String albumName;
    private ArtistDTO artist;
    private Genre genre;
    private String artUrl;
    private int releaseYear;
    private int stockQuantity;
}
