# Android Interview Question Generator

A modern Android application that uses the Gemini AI model to generate technical interview questions on demand. This project demonstrates the integration of Google's GenAI SDK with Jetpack Compose and modern Android architecture.

## 🚀 Features

- **AI-Powered Questions**: Generates unique Android interview questions using `gemini-2.0-flash`.
- **Difficulty Grading**: Each question comes with a difficulty level (Easy, Medium, Hard).
- **Comprehensive Answers**: Provides detailed explanations for each technical question.
- **Modern UI**: Built entirely with Jetpack Compose and Material Design 3.
- **Reactive Architecture**: Uses StateFlow and ViewModel for a robust, reactive data flow.

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Asynchronous Programming**: Kotlin Coroutines & Flow
- **AI Integration**: [Google GenAI SDK](https://github.com/google/generative-ai-android)
- **JSON Parsing**: Kotlin Serialization
- **Dependency Injection**: Manual (ViewModelProvider)
- **Build System**: Gradle Kotlin DSL

## 📋 Prerequisites

- Android Studio Ladybug or newer.
- A Gemini API Key from [Google AI Studio](https://aistudio.google.com/).

## ⚙️ Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/AndroidInterviewQuestion.git
   ```

2. **Add your API Key**:
   Create a `local.properties` file in the root directory (if it doesn't exist) and add your Gemini API key:
   ```properties
   GEMINI_API_KEY=your_api_key_here
   ```

3. **Build and Run**:
   Open the project in Android Studio and run the `app` module on an emulator or physical device.

## 🏗️ Project Structure

- `MainActivity.kt`: The main entry point, hosting the Compose UI.
- `MainViewModel.kt`: Handles AI interactions and state management.
- `InterviewQuestions.kt`: Data model for the AI-generated content.
- `ui/theme/`: Contains Material 3 theme definitions.

## 🛡️ License

This project is licensed under the MIT License - see the LICENSE file for details.
