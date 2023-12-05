package com.example.actoresrecyclerview.data

import com.example.actoresrecyclerview.domain.modelo.Actores
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStream

class RepositoryActores(file: InputStream? = null) {

    companion object {
        private val lista = mutableListOf<Actores>()
    }

    init {
        if (lista.isEmpty()) {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val listOfCardsType = Types.newParameterizedType(
                MutableList::class.java,
                Actores::class.java
            )
            val ejemplo = file?.bufferedReader()?.readText()?.let { contenidoFichero ->
                moshi.adapter<List<Actores>>(listOfCardsType)
                    .fromJson(contenidoFichero)
            }

            ejemplo?.let { lista.addAll(it) }
        }
    }

    fun getListaActores(): List<Actores> {
        return lista
    }

    fun getActorId(id: Int): Actores {
        return lista.firstOrNull { it.id == id } ?: lista[0]
    }

    fun addActor(actor: Actores): Boolean {
        actor.id = getAutoId()
        return lista.add(actor)

    }

    fun deleteActor(actor: Actores) = lista.remove(actor)

    fun updateActores(actorAntiguo: Actores, actorActualizado: Actores) {
        actorActualizado.id = actorAntiguo.id
        lista[lista.indexOf(actorAntiguo)] = actorActualizado
    }

    fun getAutoId(): Int {
        return lista.maxOfOrNull { it.id }?.plus(1) ?: 1
    }

    fun listEmpty(): Boolean {
        return lista.isEmpty()
    }

    fun hayRepetidos(actor: Actores): Boolean {
        return lista.any {
            it.nombre == actor.nombre &&
                    it.vivo == actor.vivo &&
                    it.peliculaFamosa == actor.peliculaFamosa &&
                    it.premiosOscar == actor.premiosOscar &&
                    it.genero == actor.genero
        }
    }


}