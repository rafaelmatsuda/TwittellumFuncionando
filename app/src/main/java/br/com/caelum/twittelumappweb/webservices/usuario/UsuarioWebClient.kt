package br.com.caelum.twittelumappweb.webservices.usuario

import br.com.caelum.twittelumappweb.modelo.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

class UsuarioWebClient(retrofit: Retrofit) {

    private val service: UsuarioService by lazy {
        retrofit.create(UsuarioService::class.java)
    }

    fun loga(
            usuario: Usuario,
            sucesso: (usuario: Usuario) -> Unit,
            falha: (Throwable) -> Unit) {

        val chamadaParaLogar = service.loga(usuario)


        chamadaParaLogar.enqueue(UsuarioCallback(sucesso, falha))
    }


    fun cria(
            usuario: Usuario,
            sucesso: (usuario: Usuario) -> Unit,
            falha: (Throwable) -> Unit
    ) {

        val chamadaPraCriar: Call<Usuario> = service.cria(usuario)

        chamadaPraCriar.enqueue(UsuarioCallback(sucesso, falha))


    }


    private interface UsuarioService {

        @POST("usuario/login")
        fun loga(@Body usuario: Usuario): Call<Usuario>


        @POST("usuario")
        fun cria(@Body usuario: Usuario): Call<Usuario>
    }
}

private class UsuarioCallback(
        val sucesso: (usuario: Usuario) -> Unit,
        val falha: (Throwable) -> Unit) : Callback<Usuario> {

    override fun onFailure(call: Call<Usuario>, t: Throwable) {

        falha(t)
    }

    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

        if (response.isSuccessful) {
            val usuarioDoServidor: Usuario? = response.body()
            usuarioDoServidor?.let(sucesso)
        }
    }
}
