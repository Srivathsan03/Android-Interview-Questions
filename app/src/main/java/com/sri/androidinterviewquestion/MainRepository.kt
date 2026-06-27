package com.sri.androidinterviewquestion

import android.util.Log
import com.google.genai.Client
import com.google.genai.errors.ClientException
import com.google.genai.types.GenerateContentConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

class MainRepository {

    private val client by lazy {
        Client.builder()
            .apiKey(BuildConfig.GEMINI_API_KEY)
            .build()
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
                return@withContext response.text() ?: "{}"
            } catch (e: ClientException) {
                val msg = e.message() ?: ""
                if (e.code() == 429) {
                    if (attempt < maxAttempts - 1) {
                        attempt++
                        kotlinx.coroutines.delay(currentDelay.milliseconds)
                        currentDelay *= 2
                        continue
                    }
                    val retryIn = if (msg.contains("retry in")) {
                        msg.substringAfter("retry in").substringBefore(".").trim()
                    } else {
                        "unknown time"
                    }
                    return@withContext "{\"error\": \"Quota exceeded. Please retry in $retryIn.\"}"
                }
                val safeMsg = msg.replace("\"", "\\\"").replace("\n", " ")
                return@withContext "{\"error\": \"Client error (${e.code()}): $safeMsg\"}"
            } catch (e: Exception) {
                e.printStackTrace()
                val safeMsg = (e.message ?: "Unknown error")
                    .replace("\"", "\\\"").replace("\n", " ")
                return@withContext "{\"error\": \"Error: $safeMsg\"}"
            }
        }
        "Failed after $maxAttempts attempts"
    }

    fun createCleanJson(json: String): String {
        return json.trim()
            .removePrefix("```json")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()
    }
}