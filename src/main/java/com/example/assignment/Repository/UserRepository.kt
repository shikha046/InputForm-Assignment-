package com.example.assignment.Repository

import com.example.assignment.Dao.UserDao
import com.example.assignment.Model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }
}