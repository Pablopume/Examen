package com.example.actoresrecyclerview.domain.usecases

import com.example.actoresrecyclerview.data.RepositoryActores

class GetActoresUseCase(private var repositoryActores: RepositoryActores) {
    operator fun invoke() = repositoryActores.getListaActores()

}