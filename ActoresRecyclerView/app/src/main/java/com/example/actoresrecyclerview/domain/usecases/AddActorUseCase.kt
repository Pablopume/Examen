package com.example.actoresrecyclerview.domain.usecases

import com.example.actoresrecyclerview.data.RepositoryActores
import com.example.actoresrecyclerview.domain.modelo.Actores

class AddActorUseCase {
    operator fun invoke(actor: Actores) = RepositoryActores().addActor(actor)

}