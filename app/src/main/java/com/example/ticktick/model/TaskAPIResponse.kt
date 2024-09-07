package com.example.ticktick.model

class TaskAPIResponse {
    var id: Int = 0
    var title: String = ""
    var description: String = ""
    var dueDate: String = ""
    var priority: String = ""
    var completed: Boolean = false


    constructor()

    constructor(
        completed: Boolean,
        priority: String,
        dueDate: String,
        description: String,
        title: String,
        id: Int
    ) {
        this.completed = completed
        this.priority = priority
        this.dueDate = dueDate
        this.description = description
        this.title = title
        this.id = id
    }

}