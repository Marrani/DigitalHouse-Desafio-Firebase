package com.gabrielmarrani.firabase

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.gabrielmarrani.firabase.model.JogoModel
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.mikhaellopez.circleview.CircleView

class CadastrarJogoActivity : AppCompatActivity() {

    private var imageURI: Uri? = null
    private var _fileSaved: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_jogo)

        val nome = findViewById<EditText>(R.id.edtNameJogo)
        val data = findViewById<EditText>(R.id.edtDateJogo)
        val descricao = findViewById<EditText>(R.id.edtDescriptionJogo)

        findViewById<CircleView>(R.id.btnUpload).setOnClickListener {
            procurarArquivo()
        }
        findViewById<ImageView>(R.id.imageUpload).setOnClickListener {
            procurarArquivo()
        }
        findViewById<MaterialButton>(R.id.btnSaveJogo).setOnClickListener {
            validarEntrada(nome, data, descricao)
        }

    }

        fun procurarArquivo() {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, CONTENT_REQUEST_CODE)
        }

        fun validarEntrada(
            nomeContainer: EditText,
            dataConteiner: EditText,
            descricaoConteiner: EditText
        ) {
            val nome = nomeContainer.text.toString()
            val data = dataConteiner.text.toString()
            val descricao = descricaoConteiner.text.toString()

            if (!nome.isNullOrBlank() && !data.isNullOrBlank() && !descricao.isNullOrBlank()) {

                if(imageURI != null){
                    enviarImagemEArmazenarNoDb(nome,data,descricao)
                }
            } else {
                Toast.makeText(
                    this,
                    "Todos os campos precisam estar preenchidos.",
                    Toast.LENGTH_LONG
                ).show()

            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == CONTENT_REQUEST_CODE && resultCode == RESULT_OK) {
                imageURI = data?.data
                val imagemContainer = findViewById<ImageView>(R.id.imagemSelecionada)
                imagemContainer.setImageURI(imageURI)
                imagemContainer.visibility = View.VISIBLE
                findViewById<ImageView>(R.id.imageUpload).visibility = View.GONE

            }
        }

        fun enviarImagemEArmazenarNoDb(nome:String, data:String, descricao:String) {
            imageURI?.run {
                val user = FirebaseAuth.getInstance().currentUser
                val firebase = FirebaseStorage.getInstance()
                val storage = firebase.getReference("uploads")

                val extension = MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(contentResolver.getType(imageURI!!))

                val fileName = "${user!!.uid}-${System.currentTimeMillis()}.${extension}"
                val fileReference = storage.child(fileName)

                fileReference.putFile(this)
                    .addOnSuccessListener {
                        // Salvar o fileReference.toString() no Realtime Database
                        Toast.makeText(
                            this@CadastrarJogoActivity,
                            "Arquivo enviado com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()
                        _fileSaved = fileName
                        gravarNoDb(nome,data,descricao)
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this@CadastrarJogoActivity,
                            it.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnProgressListener {
                        Log.d("PROGRESS", it.toString())
                    }
            }
        }

        private fun gravarNoDb(nome: String, data: String, descricao: String) {
            if (_fileSaved != null) {
                val user = FirebaseAuth.getInstance().currentUser
                val db = FirebaseDatabase.getInstance()
                val ref = db.getReference(user!!.uid).child(nome)
                ref.setValue(JogoModel(nome, data, descricao, _fileSaved!!))
                    .addOnFailureListener {
                        Toast.makeText(
                            this@CadastrarJogoActivity,
                            it.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnSuccessListener {
                        Toast.makeText(
                            this@CadastrarJogoActivity,
                            "teste",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }

    companion object {
        const val CONTENT_REQUEST_CODE = 1
    }
}