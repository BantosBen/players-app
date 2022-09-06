package com.nerdpros.newshome.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nerdpros.newshome.data.local.entity.UserEntity

/**
 * @Author: Angatia Benson
 * @Date: 26/08/2022
 * Copyright (c) 2022 Bantechnis
 */

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(userEntity: UserEntity)

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Delete
    suspend fun removeUser(userEntity: UserEntity)

    @Query("SELECT * FROM `user` LIMIT 1")
    suspend fun getUser(): UserEntity
}