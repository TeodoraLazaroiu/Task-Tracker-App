package com.example.ticktick.model

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

open class Task : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var userId: String? = ""
    var taskName: String = ""
    var completed: Boolean = false
    var dueDate: RealmInstant? = null

    constructor()

    constructor(userId: String?, taskName: String, dueDate: RealmInstant?) {
        this.userId = userId
        this.taskName = taskName
        this.completed = false
        this.dueDate = dueDate
    }
}