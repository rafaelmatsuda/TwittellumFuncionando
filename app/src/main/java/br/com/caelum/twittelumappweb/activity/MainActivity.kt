package br.com.caelum.twittelumappweb.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import br.com.caelum.twittelumappweb.R
import br.com.caelum.twittelumappweb.fragment.BuscadorDeTweetsFragment
import br.com.caelum.twittelumappweb.fragment.ListaTweetsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mainBottomNavigation.setOnNavigationItemSelectedListener { opcao: MenuItem ->

            when (opcao.itemId) {
                R.id.menuLista -> {
                    exibe(ListaTweetsFragment())
                    true
                }
                R.id.menuPesquisar -> {
                    exibe(BuscadorDeTweetsFragment())
                    true
                }
                R.id.menuMapa -> {
                    exibe(Fragment())
                    true
                }
                else -> {
                    false
                }
            }
        }

        mainBottomNavigation.selectedItemId = R.id.menuLista



        mainFab.setOnClickListener {

            val intent = Intent(this, TweetActivity::class.java)
            startActivity(intent)

        }



    }

    private fun exibe(fragment: Fragment) {

        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.frameMain, fragment)

        transaction.commit()

    }
}




















