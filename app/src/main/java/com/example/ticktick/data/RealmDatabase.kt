package com.example.ticktick.data

import com.example.ticktick.model.Task
import com.example.ticktick.model.User
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.query.find

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

    fun getTaskForUser(userId: String?) : RealmResults<Task> {
        return realm.query<Task>("userId == $0", userId).find()
    }

    fun completeTask(task: Task) {
        realm.writeBlocking {
            val newTask = findLatest(task)
            newTask?.completed = !newTask?.completed!!
        }
    }
}