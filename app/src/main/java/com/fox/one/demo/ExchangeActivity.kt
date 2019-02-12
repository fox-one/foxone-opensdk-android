package com.fox.one.demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.foxone.exchange.ex.market.MarketActivity
import com.foxone.exchange.ex.market.fav.FavFragment
import com.foxone.exchange.framework.account.AccountManager

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

        favFragment.onEmptyViewClick = {
            if (AccountManager.isLogin()) {
                MarketActivity.start(this@ExchangeActivity)
            } else {
                AccountManager.launchLoginUI(this)
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ExchangeActivity::class.java))
        }
    }
}