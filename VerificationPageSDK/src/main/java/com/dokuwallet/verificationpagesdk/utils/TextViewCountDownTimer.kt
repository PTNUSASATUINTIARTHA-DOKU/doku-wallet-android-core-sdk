package com.dokuwallet.verificationpagesdk.utils

import android.os.CountDownTimer
import android.view.View
import android.widget.TextView


class TextViewCountDownTimer(
    millisInFuture: Long,
    countDownInterval: Long,
    private val timerTextView: TextView
) :
    CountDownTimer(millisInFuture, countDownInterval) {
    var timeRemaining = 0

    var isTimerStarted = false
        private set
    var isTimerFinished = false
        private set

    override fun onTick(millisUntilFinished: Long) {
        if (isTimerFinished) return
        isTimerStarted = true
        timerTextView.visibility = View.VISIBLE
        timeRemaining = ((millisUntilFinished + 500) / 1000).toInt()
        if (timeRemaining >= 1) {
            timerTextView.text = timeRemaining.toString()
        }
    }

    override fun onFinish() {
        isTimerFinished = true
        timerTextView.visibility = View.GONE
    }
}