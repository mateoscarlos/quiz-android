package com.bignerdranch.android.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    public var currentIndex = 0
    public var isCheater = BooleanArray(questionBank.size) { false }

    public val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    public val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    public fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
}