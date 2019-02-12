package com.fox.one.demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.foxone.exchange.ex.market.fav.FavFragment

/**
 * 交易所.行情
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-02-11
 */
class ExchangeActivity: AppCompatActivity() {

    private val favFragment = FavFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_exchange)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container_exchange, favFragment)
            .commitAllowingStateLoss()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ExchangeActivity::class.java))
        }
    }
}