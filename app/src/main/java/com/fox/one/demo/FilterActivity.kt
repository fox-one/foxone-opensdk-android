package com.fox.one.demo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.foxone.exchange.ex.market.FilterPairListFragment

/**
 * class description here
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2019-03-13
 */
class FilterActivity: AppCompatActivity() {

    private val filterPairListFragment = FilterPairListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_filter)

        filterPairListFragment.setFilter(mutableListOf("BTCUSDT", "CNBXIN", "CNBUSDT", "EOSXIN"))

        supportFragmentManager.beginTransaction()
            .replace(R.id.filter_content, filterPairListFragment)
            .commitNowAllowingStateLoss()
    }

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, FilterActivity::class.java))
        }
    }
}