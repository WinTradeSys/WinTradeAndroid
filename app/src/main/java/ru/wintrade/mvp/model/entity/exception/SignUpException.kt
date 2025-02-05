package ru.wintrade.mvp.model.entity.exception

import com.google.gson.annotations.Expose

class SignUpException(
    @Expose
    val username: List<String>?,
    @Expose
    val password: List<String>?,
    @Expose
    val email: List<String>?,
    @Expose
    val phone: List<String>?
)