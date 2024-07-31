package com.example.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var topeira: ImageView
    private val handler = Handler(Looper.getMainLooper())
    val VELOCIDADE_INICIAL = 1500L
    private var acertos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topeira = findViewById(R.id.imagemTopeira)
        val pontuacaoAtual: TextView = findViewById(R.id.textViewPontuacaoAtual)


        topeira.setOnClickListener {
            acertos++
            pontuacaoAtual.text = "Acertos: $acertos"
        }

        val moverTopera = object : Runnable {
            override fun run() {
                val maxX = resources.displayMetrics.widthPixels - topeira.width
                val maxY = resources.displayMetrics.heightPixels - topeira.height
                val randomX = Random.nextInt(maxX)
                val randomY = Random.nextInt(maxY)
                topeira.x = randomX.toFloat()
                topeira.y = randomY.toFloat()
                handler.postDelayed(this, VELOCIDADE_INICIAL)
            }
        }


        handler.post(moverTopera)
    }
}

