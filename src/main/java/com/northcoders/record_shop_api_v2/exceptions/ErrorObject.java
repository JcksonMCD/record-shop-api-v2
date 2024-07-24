package com.northcoders.record_shop_api_v2.exceptions;

import lombok.Data;

import java.util.Date;

// Class used to package error information in a clear and readable format. Improves user experience.
@Data
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private Date timeStamp;
}
