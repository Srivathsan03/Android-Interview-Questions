package com.sri.androidinterviewquestion

import kotlinx.serialization.Serializable

@Serializable
data class InterviewQuestionsList(
    val list: List<InterviewQuestions> = listOf(),
    val error: String? = null
)

@Serializable
data class InterviewQuestions(
    val question: String = "",
    val answer: String = "",
    val difficulty: String = ""
)
