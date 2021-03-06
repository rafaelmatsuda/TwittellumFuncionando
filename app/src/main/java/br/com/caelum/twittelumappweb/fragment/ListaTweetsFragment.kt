package br.com.caelum.twittelumappweb.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.caelum.twittelumappweb.R
import br.com.caelum.twittelumappweb.adapter.TweetAdapter
import br.com.caelum.twittelumappweb.viewmodel.TweetViewModel
import br.com.caelum.twittelumappweb.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.lista_tweets_fragment.view.*

class ListaTweetsFragment : Fragment() {

    private val viewModel: TweetViewModel by lazy {
        ViewModelProviders.of(activity!!, ViewModelFactory).get(TweetViewModel::class.java)
    }



    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.lista_tweets_fragment, container, false)

        view.swipe.setOnRefreshListener { viewModel.carregaTweets() }

        view.swipe.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN, Color.CYAN, Color.GRAY, Color.MAGENTA)

        viewModel.lista().observe(this, Observer {

            it?.let { tweets ->
                val adapter = TweetAdapter(tweets)

                view.listaTweetsFragment.adapter = adapter
            }
        })


        return view

    }

    override fun onResume() {
        super.onResume()
        viewModel.lista().observe(this, Observer { lista ->
            view?.swipe?.isRefreshing = false
            view?.listaTweetsFragment?.adapter = TweetAdapter(lista!!)
        })
    }
}

