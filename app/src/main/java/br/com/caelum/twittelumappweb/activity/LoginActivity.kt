package br.com.caelum.twittelumappweb.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.caelum.twittelumappweb.R
import br.com.caelum.twittelumappweb.modelo.Usuario
import br.com.caelum.twittelumappweb.viewmodel.UsuarioViewModel
import br.com.caelum.twittelumappweb.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val viewModel: UsuarioViewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory).get(UsuarioViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel.usuarioLogado().observe(this, Observer { estaLogado ->

            estaLogado?.let {
                if (estaLogado) {
                    vaiParaMain()
                }
            }
        })

        viewModel.falha().observe(this, Observer {
            Toast.makeText(this, it!!.message, Toast.LENGTH_LONG).show()
        })


        login_criar.setOnClickListener {
            viewModel.criaConta(usuarioDaTela())
        }

        login_entrar.setOnClickListener {
            viewModel.loga(usuarioDaTela())
        }


    }

    private fun vaiParaMain() {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun usuarioDaTela(): Usuario {

        val nome = login_campoNome.text.toString()
        val username = login_campoUsername.text.toString()
        val senha = login_campoSenha.text.toString()

        return Usuario(nome = nome, username = username, senha = senha)

    }

}