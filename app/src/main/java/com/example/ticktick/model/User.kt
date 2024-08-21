package com.example.ticktick.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class User : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var email: String = ""
    var password: String = ""

    constructor()

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }
}