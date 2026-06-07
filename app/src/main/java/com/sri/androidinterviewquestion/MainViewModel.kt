package com.sri.androidinterviewquestion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.genai.Client
import com.google.genai.errors.ClientException
import com.google.genai.types.GenerateContentConfig
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

class MainViewModel : ViewModel() {

    private val client by lazy {
        Client.builder()
            .apiKey(BuildConfig.GEMINI_API_KEY)
            .build()
    }

    val interviewQuestionsListStateFlow: MutableStateFlow<InterviewQuestionsList?> =
        MutableStateFlow(null)

    private val prompt = """
        Generate Android interview questions.
        Generate atleast 20 questions for a 10 year experienced candidate.
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
            parseJson(geminiResponse(prompt = prompt))
        }
    }

    suspend fun geminiResponse(
        prompt: String
    ): String = withContext(Dispatchers.IO) {
        var attempt = 0
        val maxAttempts = 3
        var currentDelay = 2000L

        while (attempt < maxAttempts) {
            try {
                Log.d("TAG", "geminiResponse: request sent")
                val response = client.models.generateContent(
                    "gemini-2.5-flash",
                    prompt,
                    GenerateContentConfig.builder()
                        .responseMimeType("application/json")
                        .build()
                )
                Log.d("TAG", "geminiResponse: ${response.text()}")
                return@withContext response.text() ?: "Empty response"
            } catch (e: ClientException) {
                val msg = e.message() ?: ""
                if (e.code() == 429) {
                    if (attempt < maxAttempts - 1) {
                        attempt++
                        kotlinx.coroutines.delay(currentDelay.milliseconds)
                        currentDelay *= 2
                        continue
                    }
                    return@withContext if (msg.contains("retry in")) {
                        "Quota exceeded. Retry in ${msg.substringAfter("retry in").trim()}."
                    } else {
                        "Quota exceeded. Please try again later."
                    }
                }
                return@withContext "Client error (${e.code()}): $msg"
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext "Error: ${e.message}"
            }
        }
        "Failed after $maxAttempts attempts"
    }

    fun parseJson(response: String) {
        try {
            val cleanedResponse = response.trim()
                .removePrefix("```json")
                .removePrefix("```")
                .removeSuffix("```")
                .trim()
            Log.d("TAG", "parseJson: cleanedResponse = $cleanedResponse")
            val interviewQuestions =
                Gson().fromJson(cleanedResponse, InterviewQuestionsList::class.java)
            interviewQuestionsListStateFlow.value = interviewQuestions
        } catch (e: Exception) {
            Log.e("TAG", "parseJson: Error parsing JSON", e)
        }
    }
}