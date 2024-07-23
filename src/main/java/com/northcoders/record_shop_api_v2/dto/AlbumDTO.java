package com.northcoders.record_shop_api_v2.dto;

import com.northcoders.record_shop_api_v2.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDTO {
    private long id;
    private String albumName;
    private ArtistDTO artist;
    private Genre genre;
    private String artUrl;
    private int releaseYear;
    private int stockQuantity;
}
