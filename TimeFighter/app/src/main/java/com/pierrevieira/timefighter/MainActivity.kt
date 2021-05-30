package com.pierrevieira.timefighter

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.pierrevieira.timefighter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val INITIAL_COUNT_DOWN = 60000L
        private const val COUNT_DOWN_INTERVAL = 1000L
        private val TAG = MainActivity::class.java.simpleName
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    private var score = 0
    private var timeLeftOnTimer: Long = INITIAL_COUNT_DOWN
    private var gameStarted = false
    private lateinit var binding: ActivityMainBinding

    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called. Score is $score")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTapMeButton()
        savedInstanceState?.let {
            restoreState(it)
        } ?: resetGame()
    }

    private fun restoreState(savedInstanceState: Bundle) {
        score = savedInstanceState.getInt(SCORE_KEY)
        timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
        restoreGame()
    }

    private fun restoreGame() {
        binding.score.text = getString(R.string.score, score)
        val restoredTime = timeLeftOnTimer / 1000
        binding.time.text = getString(R.string.time_left, restoredTime)
        countDownTimer = createCountDownTimer(timeLeftOnTimer)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCORE_KEY, score)
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
        countDownTimer.cancel()
        Log.d(TAG, "onSaveInstanceState: Saving Score $score & Time Left: $timeLeftOnTimer")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called.")
    }

    private fun setupTapMeButton() {
        binding.button.setOnClickListener {
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            it.startAnimation(bounceAnimation)
            if (!gameStarted) startGame()
            incrementScore()
        }
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun incrementScore() {
        score++
        binding.score.text = getString(R.string.score, score)
        binding.score.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink))
    }

    private fun resetGame() {
        countDownTimer = createCountDownTimer(INITIAL_COUNT_DOWN)
        score = 0
        binding.score.text = getString(R.string.score, score)
        val initalTimeLeft = INITIAL_COUNT_DOWN / 1000
        binding.time.text = getString(R.string.time_left, initalTimeLeft)
        gameStarted = false
    }

    private fun createCountDownTimer(initialCountDown: Long) = object : CountDownTimer(
        initialCountDown,
        COUNT_DOWN_INTERVAL
    ) {
        override fun onTick(millisUntilFinished: Long) {
            timeLeftOnTimer = millisUntilFinished
            val timeLeft = millisUntilFinished / 1000
            binding.time.text = getString(R.string.time_left, timeLeft)
        }

        override fun onFinish() {
            endGame()
        }
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.game_over_message, score), Toast.LENGTH_SHORT)
            .show()
        resetGame()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_about) {
            showInfo()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showInfo() {
        val dialogTitle = getString(R.string.aboutTitle)
        val dialogMessage = getString(R.string.aboutMessage)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }
}