package br.com.caelum.twittelumappweb.data

import android.arch.lifecycle.MutableLiveData
import br.com.caelum.twittelumappweb.modelo.Usuario
import br.com.caelum.twittelumappweb.webservices.usuario.UsuarioWebClient

class UsuarioRepository(private val webClient: UsuarioWebClient) {

    val usuarioLogado: MutableLiveData<Boolean> = MutableLiveData()
    val usuarioDaSessao: MutableLiveData<Usuario> = MutableLiveData()
    val erro: MutableLiveData<Throwable> = MutableLiveData()

    fun criarConta(usuario: Usuario) {
        webClient.cria(usuario, sucesso, falha)
    }

    fun login(usuario: Usuario) {

        webClient.loga(usuario, sucesso, falha)
    }

    private val sucesso = { usuario: Usuario ->

        usuarioLogado.value = true

        usuarioDaSessao.value = usuario
    }

    private val falha = { falha: Throwable ->
        usuarioLogado.value = false
        erro.value = falha

    }
}