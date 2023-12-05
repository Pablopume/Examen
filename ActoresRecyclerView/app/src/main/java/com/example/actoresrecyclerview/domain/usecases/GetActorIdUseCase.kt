package com.example.actoresrecyclerview.domain.usecases

import com.example.actoresrecyclerview.data.RepositoryActores

class GetActorIdUseCase {
    operator fun invoke(id: Int) = RepositoryActores().getActorId(id)
}