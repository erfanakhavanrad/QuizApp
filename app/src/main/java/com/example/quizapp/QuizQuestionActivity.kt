package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionActivity : AppCompatActivity() {
    lateinit var tv_option_one: TextView
    lateinit var tv_option_two: TextView
    lateinit var tv_option_three: TextView
    lateinit var tv_option_four: TextView
    lateinit var iv_image: ImageView
    lateinit var progressBar: ProgressBar
    lateinit var tv_progress: TextView
    lateinit var btn_submit: Button

    //    val questionsList = Constants.getQuestions()
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Questions>? = null
    private var mSelectedOptionPosition: Int = 0
    private var correctAnswersL: Int = 0
    private var mUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)
        tv_option_one = findViewById(R.id.tv_option_one)
        tv_option_two = findViewById(R.id.tv_option_two)
        tv_option_three = findViewById(R.id.tv_option_three)
        tv_option_four = findViewById(R.id.tv_option_four)
        iv_image = findViewById(R.id.iv_image)
        progressBar = findViewById(R.id.progressBar)
        tv_progress = findViewById(R.id.tv_progress)
        btn_submit = findViewById(R.id.btn_submit)
        mQuestionsList = Constants.getQuestions()
        mUserName = intent.getStringExtra(Constants.USER_NAME)
        setQuestion()

        tv_option_one.setOnClickListener { selectedOptionView(tv_option_one, 1) }
        tv_option_two.setOnClickListener { selectedOptionView(tv_option_two, 2) }
        tv_option_three.setOnClickListener { selectedOptionView(tv_option_three, 3) }
        tv_option_four.setOnClickListener { selectedOptionView(tv_option_four, 4) }
        btn_submit.setOnClickListener {
            if (mSelectedOptionPosition == 0) {
                mCurrentPosition++

                when {
                    mCurrentPosition <= mQuestionsList!!.size -> {
                        setQuestion()
                    }
                    else -> {
                      val intent = Intent(this, ResultActivity::class.java)
                       intent.putExtra(Constants.USER_NAME, mUserName)
                       intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswersL)
                       intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                        startActivity(intent)
                    }
                }

            } else {
                val question = mQuestionsList?.get(mCurrentPosition - 1)
                if (question!!.correctAnswer != mSelectedOptionPosition) answerView(
                    mSelectedOptionPosition,
                    R.drawable.wrong_option_border_bg
                )
                else correctAnswersL ++
                answerView(question.correctAnswer, R.drawable.correct_option_border_bg)
                if (mCurrentPosition == mQuestionsList!!.size) btn_submit.text = "Finish"
                else btn_submit.text = "Go to the next question"
                mSelectedOptionPosition = 0
            }
        }

//        for (i in 0 until questionsList.size) {
//            Toast.makeText(this, questionsList[i].correctAnswer.toString(), Toast.LENGTH_SHORT).show()
//        }


//        Log.e("TAG for", "onCreate: ${questionsList.size}}")


    }

    private fun setQuestion() {
        val question: Questions = mQuestionsList!![mCurrentPosition - 1]

        defaultOptionsView()

        if (mCurrentPosition == mQuestionsList!!.size) btn_submit.text = "Finish"
        else btn_submit.text = "Submit"

        progressBar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.max

        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        for (options in options) {
            options.setTextColor(Color.parseColor("#7a8089"))
            options.typeface = Typeface.DEFAULT
            options.background =
                ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363a43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background =
            ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun answerView(answer: Int, drawable: Int) {
        when (answer) {
            1 -> {
                tv_option_one.background = ContextCompat.getDrawable(this, drawable)
            }
            2 -> {
                tv_option_two.background = ContextCompat.getDrawable(this, drawable)
            }
            3 -> {
                tv_option_three.background = ContextCompat.getDrawable(this, drawable)
            }
            4 -> {
                tv_option_four.background = ContextCompat.getDrawable(this, drawable)
            }
        }
    }

}