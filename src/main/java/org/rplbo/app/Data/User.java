package org.rplbo.app.Data;

public class User {
    // Attribute
    private String password;
    private String role;
    private  String username;
    private String email;

    // Constructor


    public User(String role,String password,  String username, String email) {
        this.role = role;
        this.password = password;
        this.username = username;
        this.email = email;
    }


    // Getter


    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
