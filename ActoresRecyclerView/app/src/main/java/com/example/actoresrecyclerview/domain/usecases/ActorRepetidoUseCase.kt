package com.example.actoresrecyclerview.domain.usecases

import com.example.actoresrecyclerview.data.RepositoryActores
import com.example.actoresrecyclerview.domain.modelo.Actores

class ActorRepetidoUseCase {
    operator fun invoke(actor: Actores) = RepositoryActores().hayRepetidos(actor)
}