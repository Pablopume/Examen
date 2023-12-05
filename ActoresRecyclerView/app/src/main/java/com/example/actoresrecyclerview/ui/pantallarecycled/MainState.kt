package com.example.actoresrecyclerview.ui.pantallarecycled

import com.example.actoresrecyclerview.domain.modelo.Actores

data class MainState(
    val lista: List<Actores> = emptyList()
)