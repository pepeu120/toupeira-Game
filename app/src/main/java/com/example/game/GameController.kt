package com.example.game

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import kotlin.random.Random

class GameController(
    private val topeira: ImageView,
    private val onScoreUpdate: (Int) -> Unit,
    private val onHighScoreUpdate: (Int) -> Unit,
    private val onReset: () -> Unit,
    private val soundPlayer: MediaPlayer
) {

    private val handler = Handler(Looper.getMainLooper())
    private val VELOCIDADE_INICIAL = 1500L
    private val TEMPO_DECREMENTO = 50L
    private var velocidadeAtual = VELOCIDADE_INICIAL
    private var acertos = 0
    private var recorde = 0

    init {
        topeira.setOnClickListener {
            acertos++
            onScoreUpdate(acertos)
            decrementarVelocidade(TEMPO_DECREMENTO)
            soundPlayer.start()
            atualizarRecorde()
        }
        iniciarMovimentoTopeira()
    }

    private fun iniciarMovimentoTopeira() {
        val moverTopeira = object : Runnable {
            override fun run() {
                moverTopeira()
                handler.postDelayed(this, velocidadeAtual)
            }
        }
        handler.post(moverTopeira)
    }

    private fun moverTopeira() {
        val maxX = topeira.resources.displayMetrics.widthPixels - topeira.width
        val maxY = topeira.resources.displayMetrics.heightPixels - topeira.height
        val randomX = Random.nextInt(maxX)
        val randomY = Random.nextInt(maxY)
        topeira.x = randomX.toFloat()
        topeira.y = randomY.toFloat()
    }

    private fun decrementarVelocidade(tempo: Long) {
        velocidadeAtual -= tempo
    }

    fun resetarJogo() {
        acertos = 0
        velocidadeAtual = VELOCIDADE_INICIAL
        onScoreUpdate(acertos)
        onReset()
    }

    private fun atualizarRecorde() {
        if (acertos > recorde) {
            recorde = acertos
            onHighScoreUpdate(recorde)
        }
    }
}
