package com.northcoders.record_shop_api_v2.dto;

import java.util.List;

public class AlbumGetAllResponse {
    private List<AlbumDTO> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean last;
}
