package com.sri.androidinterviewquestion

import kotlinx.serialization.Serializable

@Serializable
data class InterviewQuestions(
    val question: String,
    val answer: String,
    val difficulty: String
)
