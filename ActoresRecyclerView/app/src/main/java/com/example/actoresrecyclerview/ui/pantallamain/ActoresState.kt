package com.example.actoresrecyclerview.ui.pantallamain

import com.example.actoresrecyclerview.domain.modelo.Actores
import com.example.actoresrecyclerview.ui.Constantes

data class ActoresState(
    val actores: Actores = Actores(
        0,
        Constantes.EMPTY, true,
        Constantes.EMPTY, 0,
        Constantes.EMPTY
    ),
    val error: String? = null,
)