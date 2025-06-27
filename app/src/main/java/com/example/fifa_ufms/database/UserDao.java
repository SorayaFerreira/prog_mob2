package com.example.fifa_ufms.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fifa_ufms.entities.User;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE userId = :id LIMIT 1")
    User getUserById(int id);
}
