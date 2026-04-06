package com.grandreserve.dto;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;

    public RegisterRequest() {}

    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }

    public void setName(String v)     { this.name = v; }
    public void setEmail(String v)    { this.email = v; }
    public void setPassword(String v) { this.password = v; }
}
