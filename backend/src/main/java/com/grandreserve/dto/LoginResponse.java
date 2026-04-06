package com.grandreserve.dto;

public class LoginResponse {
    private String token;
    private Long   id;
    private String username;
    private String name;
    private String role;
    private String email;

    public LoginResponse() {}

    public LoginResponse(String token, Long id, String username, String name, String role, String email) {
        this.token = token; this.id = id; this.username = username;
        this.name = name; this.role = role; this.email = email;
    }

    public String getToken()    { return token; }
    public Long   getId()       { return id; }
    public String getUsername() { return username; }
    public String getName()     { return name; }
    public String getRole()     { return role; }
    public String getEmail()    { return email; }
}
