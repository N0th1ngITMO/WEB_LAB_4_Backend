package ru.kkettch.dataTransferObject;

public class JwtRespDto {
    private long tokenExpirationDate;
    private String tokenType;
    private String message;

    public JwtRespDto(long tokenExpirationDate, String tokenType) {
        this.tokenExpirationDate = tokenExpirationDate;
        this.tokenType = tokenType;
    }

    public JwtRespDto(long tokenExpirationDate, String tokenType, String message) {
        this.tokenExpirationDate = tokenExpirationDate;
        this.tokenType = tokenType;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
