package br.com.caelum.twittelumappweb.webservices

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InicializadorDoRetrofit {

    val retrofit = Retrofit.Builder()
            .baseUrl("https://4fad3ac3.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}