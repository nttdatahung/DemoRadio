package com.example.baseproject.data.local.daos

import androidx.room.*
import com.example.baseproject.data.model.EmailObject

@Dao
interface EmailDao {
    @Query("SELECT * FROM email_object")
    suspend fun getAll(): List<EmailObject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(emailObject: EmailObject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(listMails: List<EmailObject>)

    @Delete
    suspend fun delete(emailObject: EmailObject)

    @Query("DELETE FROM email_object")
    suspend fun deleteAll() //todo delete by email and folder

    @Update
    suspend fun update(emailObject: EmailObject?)
}