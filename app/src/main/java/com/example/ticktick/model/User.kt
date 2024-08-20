package com.example.ticktick.model

import io.realm.annotations.RealmClass
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

public class User : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var email: String = ""
    var password: String = ""
}