package com.example.ticktick.data

import com.example.ticktick.model.User

interface IDatabaseRepository {
    fun createUser(user: User)
}