package com.oakraw.a2c2psample.options

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ccpp.pgw.sdk.android.builder.CardPaymentBuilder
import com.ccpp.pgw.sdk.android.builder.TransactionResultRequestBuilder
import com.ccpp.pgw.sdk.android.callback.APIResponseCallback
import com.ccpp.pgw.sdk.android.core.PGWSDK
import com.ccpp.pgw.sdk.android.model.PaymentCode
import com.ccpp.pgw.sdk.android.model.api.TransactionResultResponse
import com.oakraw.a2c2psample.AppController
import com.oakraw.a2c2psample.databinding.ActivityCreditCardBinding
import com.tenbis.library.consts.CardType
import com.tenbis.library.listeners.OnCreditCardStateChanged
import com.tenbis.library.models.CreditCard


class CreditCardActivity : AppCompatActivity() {
    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, CreditCardActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private var creditCardInfo: CreditCard? = null

    private val binding: ActivityCreditCardBinding by lazy {
        ActivityCreditCardBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as AppController).analytics?.track("test 2c2p credit card")

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Credit Card"


        binding.payView.addOnCreditCardStateChangedListener(object : OnCreditCardStateChanged {
            override fun onCreditCardCvvValid(cvv: String) {
            }

            override fun onCreditCardExpirationDateValid(month: Int, year: Int) {
            }

            override fun onCreditCardNumberValid(creditCardNumber: String) {
            }

            override fun onCreditCardTypeFound(cardType: CardType) {
            }

            override fun onCreditCardValid(creditCard: CreditCard) {
                this@CreditCardActivity.creditCardInfo = creditCard
                binding.btnPay.isEnabled = true
            }

            override fun onInvalidCardTyped() {
            }
        })

        binding.btnPay.isEnabled = false
        binding.btnPay.setOnClickListener { checkout() }
    }

    private fun checkout() {
        val paymentToken =
            "kSAops9Zwhos8hSTSeLTUQLsdpodK3x2hKWkxbLAEMFtNABeaMLGfTaNIY1Mmqcr27LSVDnZqhZy4MrzEfQ5voC7FOv0Gxeerzj8lGUMrl7D4uAYiY48kdV+S7z1ipTU"

        val paymentRequest = CardPaymentBuilder(PaymentCode("CC"), creditCardInfo?.cardNumber)
            .setExpiryMonth(creditCardInfo?.expiryMonth ?: -1)
            .setExpiryYear(creditCardInfo?.expiryYear?.plus(2000) ?: -1)
            .setSecurityCode(creditCardInfo?.cvv)
            .build()

        val transactionResultRequest =
            TransactionResultRequestBuilder(paymentToken)
                .with(paymentRequest)
                .build()


        binding.progressCircular.isVisible = true

        PGWSDK.getInstance().proceedTransaction(
            transactionResultRequest,
            object : APIResponseCallback<TransactionResultResponse> {
                override fun onResponse(p0: TransactionResultResponse?) {
                    Log.d("APIResponseCallback onResponse", p0.toString())
                    Toast.makeText(this@CreditCardActivity, "Success", Toast.LENGTH_SHORT).show()
                    binding.progressCircular.isVisible = false
                }

                override fun onFailure(p0: Throwable?) {
                    Log.e("APIResponseCallback onFailure", p0.toString())
                    Toast.makeText(
                        this@CreditCardActivity,
                        "Error: ${p0?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressCircular.isVisible = false
                }

            })
    }
}
