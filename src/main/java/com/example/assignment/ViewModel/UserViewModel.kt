package com.example.assignment.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.Model.User
import com.example.assignment.Repository.UserRepository
import kotlinx.coroutines.launch

open class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    open fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }
}
