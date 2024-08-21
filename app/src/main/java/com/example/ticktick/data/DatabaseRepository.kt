package com.example.ticktick.data

import android.util.Log
import com.example.ticktick.model.User
import io.realm.Realm

class DatabaseRepository : IDatabaseRepository {
    private var realm: Realm = Realm.getDefaultInstance()
    private var errorTag = "database error"

    override fun createUser(user: User) {
        try {
            realm.executeTransaction {
                realm.insertOrUpdate(user)
            }
        }
        catch (ex: Exception)
        {
            Log.e(errorTag, ex.message.toString())
        }
    }
}