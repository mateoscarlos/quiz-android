package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

private const val EXTRA_ANSWER = "com.bignerdranch.android.geoquiz.answer_is_true"
public const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var apiLevelTextView: TextView
    private lateinit var showAnswerButton: Button

    private var answer = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        apiLevelTextView = findViewById(R.id.apiLevel_text_view)

        val buildNumber = Build.VERSION.SDK_INT.toString()
        apiLevelTextView.text = getString(R.string.api_level).plus(" ").plus(buildNumber)

        // intent -> Return the intent that started this activity (Activity.getIntent())
        answer = intent.getBooleanExtra(EXTRA_ANSWER, false)

        showAnswerButton.setOnClickListener {
            val answerText = when {
                answer -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)

            setAnswerShownResult()
        }
    }

    // It sends back that the answer was shown.
    private fun setAnswerShownResult() {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, true)
        }
        setResult(Activity.RESULT_OK, data)
    }

    public companion object {
        // Will be call when the parent Activity starts 'this'. It send
        fun newIntent(packageContext: Context, answer: Boolean) : Intent {
            // Intent(Context, Class)   ->  Class: Activity class that the Activity Manager should start
            //                              Context: Which application package the activity class can be found in
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER, answer)
            }
        }
    }
}