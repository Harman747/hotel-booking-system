package com.grandreserve.dto;

public class LoginRequest {
    private String identifier;   // email (customers) or username (staff)
    private String password;
    private String role;         // CUSTOMER | MANAGEMENT

    public LoginRequest() {}

    public String getIdentifier() { return identifier; }
    public String getPassword()   { return password; }
    public String getRole()       { return role; }

    public void setIdentifier(String v) { this.identifier = v; }
    public void setPassword(String v)   { this.password = v; }
    public void setRole(String v)       { this.role = v; }
}
