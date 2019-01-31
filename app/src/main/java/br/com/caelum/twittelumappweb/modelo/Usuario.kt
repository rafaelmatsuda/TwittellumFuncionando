package br.com.caelum.twittelumappweb.modelo

data class Usuario(
        val username: String,
        val senha: String,
        val nome: String,
        val foto: String? = null,
        val id: Long = 0
)