# Android Interview Question Generator

Android Interview Question Generator is an Android application that demonstrates how to generate structured AI responses using Google's Gemini API.

The project focuses on transforming Gemini-generated JSON into strongly typed Kotlin models and displaying the results using a modern Jetpack Compose UI.

## Features

- AI-generated Android interview questions
- Structured JSON responses from Gemini
- Difficulty classification (Easy, Medium, Hard)
- Detailed explanations for each answer
- Automatic JSON deserialization using Kotlin Serialization
- Reactive UI powered by StateFlow
- Jetpack Compose and Material 3 interface

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Asynchronous Programming**: Kotlin Coroutines & Flow
- **AI Integration**: [Google GenAI SDK](https://github.com/google/generative-ai-android)
- **JSON Parsing**: Kotlin Serialization
- **Build System**: Gradle Kotlin DSL

## Architecture

```text
Compose UI
    ↓
MainViewModel
    ↓
Gemini API
    ↓
Structured JSON Response
    ↓
Kotlin Serialization
    ↓
Interview Question Model
    ↓
Compose UI
```

## Prerequisites

- Android Studio Ladybug or newer.
- A Gemini API Key from [Google AI Studio](https://aistudio.google.com/).

## Setup Instructions

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

## AI Concepts Explored

### Structured Output

Using Gemini to generate interview questions in a predefined JSON schema instead of free-form text.

### JSON Parsing

Converting AI-generated JSON into strongly typed Kotlin data models using Kotlin Serialization.

### Prompt Engineering

Designing prompts that consistently produce valid and parsable JSON responses.

### State Management

Managing AI-generated content using ViewModel and StateFlow.

### AI-Powered Content Generation

Generating Android interview questions, difficulty levels, and explanations dynamically using Gemini.

## Roadmap

### Completed

- [x] Gemini Integration
- [x] Structured JSON Output
- [x] Kotlin Serialization
- [x] StateFlow State Management
- [x] Jetpack Compose UI

### Future Improvements

- [ ] Save Generated Questions
- [ ] Question Categories
- [ ] Offline Cache (Room)
- [ ] Share/Export Questions

## Project Structure

- `MainActivity.kt`: The main entry point, hosting the Compose UI.
- `MainViewModel.kt`: Handles AI interactions and state management.
- `MainRepository.kt`: Handles Gemini API communication and JSON processing.
- `InterviewQuestions.kt`: Data model for the AI-generated content.
- `ui/theme/`: Contains Material 3 theme definitions.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
