package com.gabrielmarrani.firabase

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class DetalheActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe)


        val nome = intent.getStringExtra("nome")!!
        val data = intent.getStringExtra("data")!!
        val descricao = intent.getStringExtra("descricao")!!
        val imagem = intent.getStringExtra("photo")!!
        val descricaoContainer = findViewById<TextView>(R.id.txtJogoDescricaoDetalhes)

        findViewById<TextView>(R.id.txtNomeJogoDetalhes).text = nome
        findViewById<TextView>(R.id.txtNomeJogoTitulo).text = nome
        findViewById<TextView>(R.id.txtAnoJogoDetalhes).text = "Lançamento: $data"
        descricaoContainer.text = descricao
        descricaoContainer.movementMethod = ScrollingMovementMethod()
        carregarImagem(imagem,findViewById(R.id.imgViewJogoDetalhe))

        findViewById<ImageView>(R.id.iconReturn).setOnClickListener {
            finish()
        }
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