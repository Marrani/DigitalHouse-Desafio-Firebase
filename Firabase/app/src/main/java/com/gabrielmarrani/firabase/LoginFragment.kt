package com.gabrielmarrani.firabase

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.gabrielmarrani.firabase.jogos.ListaJogosActivity
import com.gabrielmarrani.firabase.view.MainActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnLogin).setOnClickListener {

            val email = view.findViewById<TextInputEditText>(R.id.txtEmailLogin)
            val senha = view.findViewById<TextInputEditText>(R.id.txtSenhaLogin)

            validarLogin(email, senha)
        }

        view.findViewById<Button>(R.id.btnRegister).setOnClickListener {

            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("regra", "register")

            startActivity(intent)

        }
    }

    private fun validarLogin(
        email: TextInputEditText?,
        senha: TextInputEditText?
    ) {

        val emailTxt = email!!.text.toString()
        val senhaTxt = senha!!.text.toString()

        if(emailTxt.isNullOrEmpty()){
            email.setError("Campo email vazio")
        } else if(senhaTxt.isNullOrEmpty()){
            senha.setError("Campo senha vazio")
        } else {
            iniciaLogin(emailTxt, senhaTxt)
        }
    }

    private fun iniciaLogin(emailText: String, passText: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(emailText, passText)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(context, ListaJogosActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "Dados incorretos", Toast.LENGTH_SHORT).show()
                }
            }
    }

}