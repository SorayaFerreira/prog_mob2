package com.example.fifa_ufms.entities;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users", indices = {@Index(value = {"email"}, unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    public int userId;
    public String name;
    public String email;
    public String passwordHash; // é imbecil guardar senha em formato texto normal
    public String photoUri; // Caminho (URI) para guardar a imagem

    public User() {}

    public User(String name, String email, String passwordHash, String photoUri) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.photoUri = photoUri;
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getPhotoUri() { return photoUri; }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}