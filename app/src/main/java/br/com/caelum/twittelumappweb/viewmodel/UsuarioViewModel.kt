package br.com.caelum.twittelumappweb.viewmodel

import android.arch.lifecycle.ViewModel
import br.com.caelum.twittelumappweb.data.UsuarioRepository
import br.com.caelum.twittelumappweb.modelo.Usuario

class UsuarioViewModel(private val repository: UsuarioRepository) : ViewModel() {

    fun criaConta(usuario: Usuario) = repository.criarConta(usuario)

    fun loga(usuario: Usuario) = repository.login(usuario)

    fun usuarioLogado() = repository.usuarioLogado

    fun usuarioDaSessao() = repository.usuarioDaSessao

    fun falha() = repository.erro

}