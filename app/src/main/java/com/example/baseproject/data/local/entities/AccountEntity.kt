package com.example.baseproject.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,
    var accountEmail: String?,
    var password: String?
) {

    override fun toString(): String {
        return "id = $id, accountEmail = $accountEmail, password = $password"
    }
}