package com.example.actoresrecyclerview.domain.usecases

import com.example.actoresrecyclerview.data.RepositoryActores
import com.example.actoresrecyclerview.domain.modelo.Actores

class UpdateActorUseCase {
    operator fun invoke(actorAntiguo: Actores, actorActualizado: Actores) =
        RepositoryActores().updateActores(actorAntiguo, actorActualizado)
}