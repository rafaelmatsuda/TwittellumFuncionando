package br.com.caelum.twittelumappweb

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream


fun Bitmap.decodificaParaBase64(): String {

    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()

    return Base64.encodeToString(byteArray, Base64.DEFAULT)

}


object Carregador {

    fun decodifica(foto: String): Bitmap {

        val decode: ByteArray = Base64.decode(foto, Base64.DEFAULT)


        return BitmapFactory.decodeByteArray(decode, 0, decode.size)

    }
}
