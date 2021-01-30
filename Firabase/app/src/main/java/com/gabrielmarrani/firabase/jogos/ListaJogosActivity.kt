package com.gabrielmarrani.firabase.jogos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabrielmarrani.firabase.CadastrarJogoActivity
import com.gabrielmarrani.firabase.DetalheJogoActivity
import com.gabrielmarrani.firabase.R
import com.gabrielmarrani.firabase.model.JogoModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListaJogosActivity : AppCompatActivity() {
    lateinit var _adapterJogo: ListaJogosAdapter
    private var _lista = mutableListOf<JogoModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_jogos)

        val db = FirebaseDatabase.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val ref = db.getReference(user!!.uid)
        val recycler = findViewById<RecyclerView>(R.id.recyclerViewGames)
        val layout = GridLayoutManager(this, 2)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                _lista.clear()
                p0.children.forEach {
                    val game = it.getValue(JogoModel::class.java)
                    Toast.makeText(this@ListaJogosActivity, game!!.nome, Toast.LENGTH_LONG).show()
                    _lista.add(game)
                }


                if (_lista.isNotEmpty()) {
                    _adapterJogo = ListaJogosAdapter(_lista) {
                        val intent =
                            Intent(this@ListaJogosActivity, DetalheJogoActivity::class.java)
                        intent.putExtra("nome", it.nome)
                        intent.putExtra("data", it.data)
                        intent.putExtra("descricao", it.descricao)
                        intent.putExtra("photo", it.imagem)
                        startActivity(intent)
                    }

                    recycler.apply {
                        this.adapter = _adapterJogo
                        this.layoutManager = layout
                    }
                    Toast.makeText(
                        this@ListaJogosActivity,
                        _adapterJogo.itemCount.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@ListaJogosActivity, p0.message, Toast.LENGTH_LONG).show()
                }
            })

            findViewById<FloatingActionButton>(R.id.btnCadastrarJogo).setOnClickListener {
                val intent = Intent(this, CadastrarJogoActivity::class.java)
                startActivity(intent)
            }

    }
}