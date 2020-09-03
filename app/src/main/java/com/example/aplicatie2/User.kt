package com.example.aplicatie2

class User(val name:String, val email: String, val team:String) {
constructor(): this("","","")
    override fun toString(): String {
        var str=name.plus(" ").plus(email).plus(" ").plus(team)
        return str
    }
}