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
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            val name = view.findViewById<TextInputEditText>(R.id.txtNomeRegister)
            val email = view.findViewById<TextInputEditText>(R.id.txtEmailRegister)
            val senha = view.findViewById<TextInputEditText>(R.id.txtSenhaRegister)
            val repeat = view.findViewById<TextInputEditText>(R.id.txtRepeatSenhaRegister)

            validarUsuario(name, email, senha, repeat)

        }
    }

    private fun validarUsuario(
        name: TextInputEditText,
        email: TextInputEditText,
        senha: TextInputEditText,
        repeat: TextInputEditText) {

        val nameTxt = name.text.toString()
        val emailTxt: String = email.text.toString()
        val senhaTxt = senha.text.toString()
        val repeatTxt = repeat.text.toString()


        if(nameTxt.isNullOrEmpty()){
            name.setError("Favor preencher campo nome")

        } else if(senhaTxt.isNullOrEmpty()){
            senha.setError("Favor preencher campo senha")
        } else if (emailTxt.isNullOrEmpty()){
            email.setError("Favor preencher campo email")
        } else if(!senhaTxt.equals(repeatTxt)) {
            repeat.setError("Favor digitar senhas iguais ")
        } else {

            criarUsuario(nameTxt, emailTxt, senhaTxt)

        }

    }

    private fun criarUsuario(name: String, email: String, senha: String) {
        val mAuth = FirebaseAuth.getInstance()

        mAuth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name).build()

                    user!!.updateProfile(profileUpdates)
                    val intent = Intent(context, ListaJogosActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(context, it.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }

}