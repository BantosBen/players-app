package com.nerdpros.newshome.model

/**
 * @Author: Angatia Benson
 * @Date: 25/08/2022
 * Copyright (c) 2022 Bantechnis
 */

data class Login(
    val email: String,
    val password: String
)

data class SignUp(
    val email: String,
    val name: String,
    val password: String
)

