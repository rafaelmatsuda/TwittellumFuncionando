package br.com.caelum.twittelumappweb.data

import android.arch.lifecycle.MutableLiveData
import br.com.caelum.twittelumappweb.modelo.Tweet
import br.com.caelum.twittelumappweb.webservices.tweet.TweetWebClient

class TweetRepository(private val webClient: TweetWebClient) {

    private val lista = MutableLiveData<List<Tweet>>()

    fun salva(tweet: Tweet) {
        webClient.salva(tweet, sucesso(), erro())
    }

    private fun erro() = { erro: Throwable -> }

    private fun sucesso() = { tweet: Tweet -> }

    private fun sucessoParaLista() = { tweets: List<Tweet> ->
        lista.postValue(tweets)
    }

    fun lista() = lista

    fun buscaTweets() = webClient.buscaTweets(sucessoParaLista(), erro())

}