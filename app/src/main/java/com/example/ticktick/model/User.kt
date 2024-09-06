package com.example.ticktick.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

open class User : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var email: String = ""
    var password: String = ""

    constructor()

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }
}