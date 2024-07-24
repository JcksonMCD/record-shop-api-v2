package com.northcoders.record_shop_api_v2.dto;

import lombok.Data;

import java.util.List;

@Data
public class AlbumGetAllResponse {
    private List<AlbumDTO> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean last;
}
