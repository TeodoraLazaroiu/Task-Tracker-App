package com.example.ticktick.model

import com.example.ticktick.R
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

open class Task : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var userId: String? = ""
    var taskName: String = ""
    var completed: Boolean = false

    constructor()

    constructor(userId: String?, taskName: String) {
        this.userId = userId
        this.taskName = taskName
        this.completed = false
    }
}