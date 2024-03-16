package com.anonymous.myapp.model

data class ChampionshipResponseModel(
    val f1: List<F1>,
    val f1_academy: List<F1Academy>,
    val f2: List<F2>,
    val fe: List<Fe>,
    val motogp: List<Motogp>,
    val wec: List<Wec>
)