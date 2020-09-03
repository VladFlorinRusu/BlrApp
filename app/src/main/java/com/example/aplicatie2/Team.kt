package com.example.aplicatie2

class Team(
    val name: String,
    var status: String,
    var points: Int,
    var users: HashMap<String, User>
) {
    constructor() : this("", "", 0, HashMap<String, User>())

    fun addNewUser(u: User) {
        users.put(u.name,u)
    }

    override fun toString(): String {
        var str: String
        str = name.plus(" ").plus(status).plus(" ").plus(points.toString()).plus(" ").plus(users)
        return str
    }


}