package com.nerdpros.newshome.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: Angatia Benson
 * @Date: 26/08/2022
 * Copyright (c) 2022 Bantechnis
 */

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val uid: String,
    val email: String,
    val name: String,
    val image: String,
    @ColumnInfo(name = "session_token") val sessionToken: String
)