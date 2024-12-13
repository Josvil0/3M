package com.example.a3mdef.modelos;

/**
 * The type Spotify token.
 */
public class SpotifyToken {

    private String access_token;
    private String token_type;
    private int expires_in;

    /**
     * Gets access token.
     *
     * @return the access token
     */
    public String getAccess_token() {
        return access_token;
    }

    /**
     * Sets access token.
     *
     * @param access_token the access token
     */
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    /**
     * Gets token type.
     *
     * @return the token type
     */
    public String getToken_type() {
        return token_type;
    }

    /**
     * Sets token type.
     *
     * @param token_type the token type
     */
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    /**
     * Gets expires in.
     *
     * @return the expires in
     */
    public int getExpires_in() {
        return expires_in;
    }

    /**
     * Sets expires in.
     *
     * @param expires_in the expires in
     */
    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}

