package com.example.lab9_1

import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var progressRabbit = 0
    private var progressTurtle = 0
    private var raceFinished = false

    private lateinit var btnStart: Button
    private lateinit var sbRabbit: SeekBar
    private lateinit var sbTurtle: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnStart = findViewById(R.id.btnStart)
        sbRabbit = findViewById(R.id.sbRabbit)
        sbTurtle = findViewById(R.id.sbTurtle)

        btnStart.setOnClickListener { startRace() }
    }

    private fun startRace() {
        btnStart.isEnabled = false
        raceFinished = false
        resetProgress()

        CoroutineScope(Dispatchers.Main).launch {
            val rabbitJob = launch { runParticipant(sbRabbit, 3, "兔子") { randomDelay() } }
            val turtleJob = launch { runParticipant(sbTurtle, 1, "烏龜") }

            joinAll(rabbitJob, turtleJob)
        }
    }

    private fun resetProgress() {
        progressRabbit = 0
        progressTurtle = 0
        sbRabbit.progress = 0
        sbTurtle.progress = 0
    }

    private suspend fun runParticipant(
        seekBar: SeekBar,
        step: Int,
        name: String,
        additionalBehavior: (suspend () -> Unit)? = null
    ) {
        while (!raceFinished && seekBar.progress < 100) {
            delay(100)
            additionalBehavior?.invoke()

            val progress = if (name == "兔子") {
                progressRabbit += step
                progressRabbit
            } else {
                progressTurtle += step
                progressTurtle
            }

            updateProgress(seekBar, progress, name)
        }
    }

    private suspend fun randomDelay() {
        if ((0..2).random() < 2) delay(300)
    }

    private fun updateProgress(seekBar: SeekBar, progress: Int, name: String) {
        seekBar.progress = progress
        if (progress >= 100 && !raceFinished) {
            raceFinished = true
            showToast("$name 勝利！")
            btnStart.isEnabled = true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
