package com.example.actoresrecyclerview.ui.pantallarecycled

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actoresrecyclerview.domain.modelo.Actores
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ItemActorBinding

class ActoresAdapter(
    private var actores: List<Actores>,
    private val onClickButton: (Int) -> Unit
) : RecyclerView.Adapter<ActoresViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActoresViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActoresViewHolder(layoutInflater.inflate(R.layout.item_actor, parent, false))
    }

    override fun onBindViewHolder(holder: ActoresViewHolder, position: Int) {
        holder.render(actores[position], onClickButton)
    }

    override fun getItemCount(): Int = actores.size


    fun cambiarLista(lista: List<Actores>) {
        actores = lista
        notifyDataSetChanged()
    }
}

class ActoresViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemActorBinding.bind(view)

    fun render(
        actor: Actores,
        onClickButton: (Int) -> Unit
    ) {
        with(binding) {
            tvNombre.text = actor.nombre
            tvPelicula.text = actor.peliculaFamosa
            button2.setOnClickListener { onClickButton(actor.id) }
        }
    }
}

