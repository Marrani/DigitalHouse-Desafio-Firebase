package com.gabrielmarrani.firabase

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.gabrielmarrani.firabase.jogos.ListaJogosActivity
import com.gabrielmarrani.firabase.view.MainActivity

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
            val intent = Intent(context, ListaJogosActivity::class.java)

            startActivity(intent)
        }

        view.findViewById<Button>(R.id.btnRegister).setOnClickListener {

            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("regra", "register")

            startActivity(intent)

        }
    }

}