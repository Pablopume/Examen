package com.example.actoresrecyclerview.domain.usecases

import com.example.actoresrecyclerview.data.RepositoryActores

class ActoresEmptyUseCase {
    operator fun invoke() = RepositoryActores().listEmpty()
}