package com.example.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageView
import android.widget.Button
import android.media.MediaPlayer
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var gameController: GameController
    private lateinit var topeira: ImageView
    private lateinit var pontuacaoAtual: TextView
    private lateinit var recordeTextView: TextView
    private lateinit var resetButton: Button
    private lateinit var soundPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topeira = findViewById(R.id.imagemTopeira)
        pontuacaoAtual = findViewById(R.id.pontuacaoAtual)
        recordeTextView = findViewById(R.id.recorde)
        resetButton = findViewById(R.id.resetButton)
        soundPlayer = MediaPlayer.create(this, R.raw.som_acerto)

        gameController = GameController(topeira,
            onScoreUpdate = { acertos ->
                pontuacaoAtual.text = acertos.toString()
            },
            onHighScoreUpdate = { recorde ->
                recordeTextView.text = recorde.toString()
            },
            onReset = {
                pontuacaoAtual.text = "0"
            },
            soundPlayer = soundPlayer
        )

        resetButton.setOnClickListener {
            gameController.resetarJogo()
        }

        // Detecção de toque fora da topeira
        findViewById<View>(android.R.id.content).setOnClickListener {
            if (it.id != R.id.imagemTopeira) {
                gameController.resetarJogo()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPlayer.release()
    }
}
