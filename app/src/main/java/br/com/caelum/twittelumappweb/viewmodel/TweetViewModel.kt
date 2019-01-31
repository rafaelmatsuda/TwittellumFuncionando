package br.com.caelum.twittelumappweb.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.caelum.twittelumappweb.data.TweetRepository
import br.com.caelum.twittelumappweb.modelo.Tweet

class TweetViewModel(private val repository: TweetRepository) : ViewModel() {


    fun salva(tweet: Tweet) = repository.salva(tweet)

    fun lista(): LiveData<List<Tweet>> {

        val lista = repository.lista()

        if (lista.value == null) {
            carregaTweets()
        }
        return lista
    }

    fun carregaTweets() = repository.buscaTweets()
}