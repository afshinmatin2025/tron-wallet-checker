package com.example.tronwalletchecker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.bitcoinj.crypto.MnemonicCode
import java.security.SecureRandom

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "wallet_balance_channel"
    private var lastGeneratedSeed: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        val input = findViewById<EditText>(R.id.inputSeedOrKey)
        val btnCheck = findViewById<Button>(R.id.btnCheckBalance)
        val btnGenerate = findViewById<Button>(R.id.btnGenerateSeed)
        val tvBalance = findViewById<TextView>(R.id.tvBalance)

        btnGenerate.setOnClickListener {
            val seedPhrase = generateSeedPhrase()
            input.setText(seedPhrase)
        }

        btnCheck.setOnClickListener {
            val seedOrKey = input.text.toString()
            val balance = checkTronBalance(seedOrKey)
            tvBalance.text = "موجودی: $balance TRX"
        }
    }

    private fun generateSeedPhrase(): String {
        val secureRandom = SecureRandom()
        val entropy = ByteArray(16)
        secureRandom.nextBytes(entropy)
        val seedWords = MnemonicCode.INSTANCE.toMnemonic(entropy)
        lastGeneratedSeed = seedWords.joinToString(" ")
        return lastGeneratedSeed!!
    }

    private fun checkTronBalance(seedOrKey: String): String {
        // اینجا منطق بررسی موجودی را پیاده‌سازی کنید.
        // برای تست، یک مقدار تصادفی برگردانید.
        return (0..100).random().toString()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "موجودی کیف پول"
            val descriptionText = "اعلام موجودی کیف پول TRON"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
