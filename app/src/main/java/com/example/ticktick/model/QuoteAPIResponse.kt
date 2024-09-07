package com.example.ticktick.model

class QuoteAPIResponse {
    var q:String = ""
    var a:String = ""
    var h:String = ""

    constructor()

    constructor(a: String, q: String, h: String) {
        this.a = a
        this.q = q
        this.h = h
    }
}