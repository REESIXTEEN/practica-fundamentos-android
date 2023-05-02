package com.example.practica_fundamentos_android.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_fundamentos_android.databinding.CardHeroeBinding
import com.example.practica_fundamentos_android.model.Heroe

class MainActivityAdapter(
    private val listHeroes: List<Heroe>
): RecyclerView.Adapter<MainActivityAdapter.MainActivityViewHolder>() {


    class MainActivityViewHolder(private var item: CardHeroeBinding) : RecyclerView.ViewHolder(item.root) {

        fun showPersonaje(heroe: Heroe) {
            item.heroeName.text = heroe.name
            item.progressBarLife.max = heroe.vidaTotal
            item.progressBarLife.progress = heroe.vidaRestante

//            item.tvName.text = personaje.nombre
//            item.tvAge.text = personaje.edad.toString()

//            item.lBackground.setOnClickListener {
//                Toast.makeText(item.root.context, "Pulsado sobre ${personaje.nombre}", Toast.LENGTH_LONG).show()
//                callback.personajeClicked(personaje)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        val binding = CardHeroeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainActivityViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listHeroes.size
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        holder.showPersonaje(listHeroes[position])
    }


}