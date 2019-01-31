package br.com.caelum.twittelumappweb.modelo

import com.google.gson.annotations.SerializedName

data class Tweet(val mensagem: String,
                 val foto: String? = null,
                 val dono: Usuario)