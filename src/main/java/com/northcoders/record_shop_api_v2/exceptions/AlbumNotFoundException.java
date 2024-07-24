package com.northcoders.record_shop_api_v2.exceptions;

import java.io.Serial;

public class AlbumNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public AlbumNotFoundException(String message) {
        super(message);
    }
}
