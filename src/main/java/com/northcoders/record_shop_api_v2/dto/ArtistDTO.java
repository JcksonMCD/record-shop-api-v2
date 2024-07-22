package com.northcoders.record_shop_api_v2.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ArtistDTO {
    private long id;
    private String name;
}
