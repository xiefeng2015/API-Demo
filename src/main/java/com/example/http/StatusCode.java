package com.example.http;

public enum StatusCode {
    OK(200),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATWAY(501),
    GATEWAY_TIMEOUT(504);

    int statusCode;
    private StatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    public int value() {
        return statusCode;
    }

}
