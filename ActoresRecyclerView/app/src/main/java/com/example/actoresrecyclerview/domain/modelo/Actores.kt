package com.example.actoresrecyclerview.domain.modelo

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Actores(
    @Json(name = Constantes.ID)
    var id: Int = 0,
    @Json(name = Constantes.NOMBRE)
    val nombre: String = Constantes.EMPTY,
    @Json(name = Constantes.ACTIVO)
    val vivo: Boolean = true,
    @Json(name = Constantes.PELICULA)
    val peliculaFamosa: String = Constantes.EMPTY,
    @Json(name = Constantes.RATING)
    val premiosOscar: Int = 0,
    @Json(name = Constantes.GENERO)
    val genero: String = Constantes.EMPTY
) : Parcelable
