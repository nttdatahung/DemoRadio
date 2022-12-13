package com.example.baseproject.data.local.daos

import androidx.room.*
import com.example.baseproject.data.local.entities.AccountEntity

@Dao
interface AccountDao {
    @Query("SELECT * FROM account")
    fun getAll(): List<AccountEntity>

    @Insert
    fun insert(user: AccountEntity)

    @Insert
    fun insertAll(vararg users: AccountEntity)

    @Delete
    fun delete(user: AccountEntity)

    @Update
    fun update(user: AccountEntity?)
}