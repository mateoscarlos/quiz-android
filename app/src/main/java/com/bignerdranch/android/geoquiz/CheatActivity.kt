package com.bignerdranch.android.geoquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"

private lateinit var answerTextView: TextView
private lateinit var showAnswerButton: Button

private var answer = false;

class CheatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answer = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        showAnswerButton.setOnClickListener {
            val answerText = when {
                answer -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
        }

    }

    companion object {
        fun newIntent(packageContext: Context, answer: Boolean) : Intent {
            // Intent(Context, Class)   ->  Class: Activity class that the Activity Manager should start
            //                              Context: Which application package the activity class can be found in
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answer)
            }
        }
    }
}