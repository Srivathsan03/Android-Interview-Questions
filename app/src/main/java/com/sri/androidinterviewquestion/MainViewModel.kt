package com.sri.androidinterviewquestion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainViewModel : ViewModel() {

    val repository = MainRepository()
    val interviewQuestionsListStateFlow: MutableStateFlow<InterviewQuestionsList?> =
        MutableStateFlow(null)

    private val prompt = """
        Generate Android interview questions.
        Generate atleast 20 questions.
        Return only JSON.
        Schema:
        {
          "list": [
            {
              "question": "",
              "answer": "",
              "difficulty": ""
            }
          ]
        }
    """.trimIndent()

    init {
        viewModelScope.launch {
            parseJson(repository.geminiResponse(prompt = prompt))
        }
    }

    fun parseJson(response: String) {
        try {
            val cleanedResponse = repository.createCleanJson(response)
            Log.d("TAG", "parseJson: cleanedResponse = $cleanedResponse")
            val json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
            val interviewQuestions = json.decodeFromString<InterviewQuestionsList>(cleanedResponse)
            interviewQuestionsListStateFlow.value = interviewQuestions
        } catch (e: Exception) {
            Log.e("TAG", "parseJson: Error parsing JSON", e)
            interviewQuestionsListStateFlow.value = InterviewQuestionsList(
                error = "Parsing Error: ${e.message}"
            )
        }
    }
}