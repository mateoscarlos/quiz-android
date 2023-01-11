package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0    // It's sent to the child activity and then received back by the parent
                                            // It's used when an activity starts more than one type of child activity
                                            // and needs to know who is reporting back

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView

    // Lazily initializing: It allows you to make the property val instead of a var
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton  = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener{
            checkAnswer(true)
        }
        falseButton.setOnClickListener{
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        cheatButton.setOnClickListener {
            // Start CheatActivity
            val answer = quizViewModel.currentQuestionAnswer // Boolean: Correct answer
            // Explicit intent (cause we start an activity in own application)
            val intent = CheatActivity.newIntent(this@MainActivity, answer)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)  // Child Activity will always return a result code using this call
                                                                // Default: Activity.RESULT_CANCELED
            // line above instead of this: startActivity(intent)
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    // This function check the user answer and get the correspondent toast
    private fun checkAnswer(userAnswer : Boolean) : Boolean {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val answer : Boolean
        val messageResId : Int

        if (userAnswer == correctAnswer) {
            messageResId = R.string.correct_toast
            answer = true // Answer is correct
        } else {
            messageResId = R.string.incorrect_toast
            answer = false // Answer is incorrect
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        return answer
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState called")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }


    // DEBUG
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    // DEBUG
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    // DEBUG
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    // DEBUG
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    // DEBUG
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}