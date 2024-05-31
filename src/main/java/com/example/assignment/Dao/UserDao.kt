package com.example.assignment.Dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.assignment.Model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)
}