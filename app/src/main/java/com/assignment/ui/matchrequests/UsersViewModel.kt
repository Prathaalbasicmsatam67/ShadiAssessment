package com.assignment.ui.matchrequests

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.data.repository.UserDataRepository
import kotlinx.coroutines.launch

class UsersViewModel @ViewModelInject constructor(
    private val repository: UserDataRepository
) : ViewModel() {


    val users = repository.getUsers()

    fun updateUserStatus(uuid: String, status: Int) = viewModelScope.launch {
        repository.updateUserStatus(uuid, status)
    }


}
