package com.example.ticktick.data

import com.example.ticktick.model.Task
import com.example.ticktick.model.User
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query

class RealmDatabase {
    val realm: Realm by lazy {
        val configuration = RealmConfiguration.create(schema = setOf(User::class, Task::class))
        Realm.open(configuration)
    }

    fun saveUser(user: User) {
        realm.writeBlocking {
            copyToRealm(user)
        }
    }

    fun saveTask(task: Task) {
        realm.writeBlocking {
            copyToRealm(task)
        }
    }

    fun getAllTasks(): List<Task> {
        return realm.query<Task>().find()
    }
}