package com.gabrielmarrani.firabase.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gabrielmarrani.firabase.LoginFragment
import com.gabrielmarrani.firabase.R
import com.gabrielmarrani.firabase.RegisterFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = intent
        val regra = intent.getStringExtra("regra")

        if(regra.equals("register")){
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.add(R.id.fragmentContainer, RegisterFragment())
            transaction.commit()
        }else {
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.add(R.id.fragmentContainer, LoginFragment())
            transaction.commit()
        }



    }
}