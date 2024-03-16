package com.anonymous.myapp.model

data class F1(
    val circuit_url: String,
    val color: String,
    val dates: List<Date>,
    val flag: String,
    val name: String,
    val results: List<Result>,
    val round: Int
)