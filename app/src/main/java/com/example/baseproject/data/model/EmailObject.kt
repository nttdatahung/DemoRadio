package com.example.baseproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "email_object")
class EmailObject(
    @PrimaryKey(autoGenerate = true) val id: Long = -1,
    var emailId: String?,
    var senderName: String?,
    var senderEmail: String?,
    var subject: String?,
    var snippet: String?
    ) {
}