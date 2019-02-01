package br.com.caelum.twittelumappweb.activity

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import br.com.caelum.twittelumappweb.R
import br.com.caelum.twittelumappweb.decodificaParaBase64
import br.com.caelum.twittelumappweb.localization.GPS
import br.com.caelum.twittelumappweb.modelo.Tweet
import br.com.caelum.twittelumappweb.viewmodel.TweetViewModel
import br.com.caelum.twittelumappweb.viewmodel.UsuarioViewModel
import br.com.caelum.twittelumappweb.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_tweet.*
import java.io.File


class TweetActivity : AppCompatActivity() {

    private lateinit var viewModel: TweetViewModel
    private val usuarioViewModel: UsuarioViewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory).get(UsuarioViewModel::class.java)
    }
    private var localFoto: String? = null

    private lateinit var gps : GPS


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet)

        gps = GPS(this);

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this, ViewModelFactory).get(TweetViewModel::class.java)

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            gps.fazBusca();
        }
        else{
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),123)
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode== 123){

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gps.fazBusca()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        gps.cancela();
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.tweet_menu, menu)

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            android.R.id.home -> finish()


            R.id.tweet_menu_cadastrar -> {

                publicaTweet()

                finish()

            }


            R.id.tweet_menu_foto -> {

                tiraFoto()

            }

        }

        return true

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                carregaFoto()
            }
        }
    }


    private fun publicaTweet() {

        val tweet = criaTweet()

        viewModel.salva(tweet)

        Log.d("tweetSalvo", "$tweet")
        Toast.makeText(this, "$tweet foi salvo com sucesso :D", Toast.LENGTH_LONG).show()
    }

    fun criaTweet(): Tweet {

        val campoDeMensagemDoTweet = findViewById<EditText>(R.id.tweet_mensagem)

        val mensagemDoTweet: String = campoDeMensagemDoTweet.text.toString()

        val foto: String? = tweet_foto.tag as String?

        val dono = usuarioViewModel.usuarioDaSessao().value

        val (latitude, longitude) = gps.coordenadas()

        return Tweet(mensagemDoTweet, foto, dono!!, latitude, longitude)
    }


    private fun tiraFoto() {

        val vaiPraCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val caminhoFoto = defineLocalDaFoto()

        vaiPraCamera.putExtra(MediaStore.EXTRA_OUTPUT, caminhoFoto)

        startActivityForResult(vaiPraCamera, 123)

    }

    private fun defineLocalDaFoto(): Uri {

        localFoto = "${getExternalFilesDir("fotos")}/${System.currentTimeMillis()}.jpg"

        val arquivo = File(localFoto)

        return FileProvider.getUriForFile(this, "TweetProvider", arquivo)
    }


    private fun carregaFoto() {

        val bitmap = BitmapFactory.decodeFile(localFoto)

        val bm = Bitmap.createScaledBitmap(bitmap, bitmap.width, bitmap.height, true)

        tweet_foto.setImageBitmap(bm)

        val fotoNaBase64 = bm.decodificaParaBase64()

        tweet_foto.tag = fotoNaBase64

        tweet_foto.scaleType = ImageView.ScaleType.FIT_XY

    }


}
