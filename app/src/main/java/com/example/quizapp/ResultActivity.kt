package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    lateinit var tv_userName: TextView
    lateinit var tv_score: TextView
    lateinit var btn_finish: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tv_userName = findViewById(R.id.tv_userName)
        tv_score= findViewById(R.id.tv_score)
        btn_finish= findViewById(R.id.btn_finish)

        val userName = intent.getStringExtra(Constants.USER_NAME)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        tv_userName.text = userName
        tv_score.text = "Your score is ${correctAnswers.toString()} of ${totalQuestions.toString()}"
        btn_finish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}