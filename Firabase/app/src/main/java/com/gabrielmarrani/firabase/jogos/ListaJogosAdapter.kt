package com.gabrielmarrani.firabase.jogos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabrielmarrani.firabase.R
import com.gabrielmarrani.firabase.model.JogoModel
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class ListaJogosAdapter (
    private val lista: MutableList<JogoModel>,
    private val listener: (JogoModel) -> Unit
) : RecyclerView.Adapter<ListaJogosAdapter.ListaJogosViewHolder>() {

    class ListaJogosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imagem = view.findViewById<ImageView>(R.id.imageViewJogo)
        private val ano = view.findViewById<TextView>(R.id.txtAnoJogo)
        private val nome = view.findViewById<TextView>(R.id.txtNomeJogo)

        fun bind(game: JogoModel) {
            nome.text = game.nome
            ano.text = game.data
            carregarImagem(game.imagem, imagem)
        }

        private fun carregarImagem(imagemName: String, imagemContainer: ImageView?) {
            val storage = FirebaseStorage.getInstance().getReference("uploads")

            storage.child(imagemName).downloadUrl.addOnSuccessListener {
                Picasso.get()
                    .load(it)
                    .into(imagemContainer)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaJogosViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false)

        return ListaJogosViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaJogosViewHolder, position: Int) {
        val item = lista[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    override fun getItemCount() = lista.size
}
