package com.oakraw.a2c2psample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oakraw.a2c2psample.databinding.ActivityMainBinding
import com.oakraw.a2c2psample.options.CreditCardActivity

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.list.adapter = PaymentAdapter { position ->
            when (position) {
                0 -> CreditCardActivity.start(this)
            }
        }.apply {
            setItem(listOf("Credit Cards"))
        }

        (application as AppController).analytics?.track("test 2c2p")
    }
}
