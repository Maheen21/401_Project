# Test Suite Documentation

This document outlines the test suite for the `LLM.py` module, which implements a conversational AI assistant for finding ingredient substitutes and suggesting recipes using the Google Generative AI API. The suite consists of four test cases located in the `./Tests` directory, each designed to validate different aspects of the module's functionality. These tests utilize the modular functions `Clear()`, `StartConv()`, and `ContinueConv()` defined in `LLM.py`, ensuring robustness across various use cases and edge conditions.

## Test Cases

### 1. `test_cli.py`
- **Purpose**: Validates the basic command-line interface (CLI) behavior of the module, mimicking the original interactive chat application.
- **Description**: This test initializes a chat session with `Clear()` and enters a loop where user input is processed using `ContinueConv()`. It supports "exit" to terminate the program and "clear" to reset the conversation, replicating the original CLI experience.
- **Functionality**:
  - Displays a welcome message and instructions.
  - Accepts user input with the prompt "[🙂 User 🙂]".
  - Prints AI responses with the prefix "[✨ Missing Ingredient Finder ✨]".
  - Handles "exit" with a goodbye message and "clear" with a reset notification.
- **Expected Outcome**: The test runs an interactive session where the AI responds to cooking-related queries, resets properly with "clear", and exits cleanly with "exit". No errors should occur unless the API fails.
- **Use Case**: Ensures the module works as a standalone CLI tool with persistent conversation context.

### 2. `prompt_test.py`
- **Purpose**: Tests the module's ability to start a conversation with a predefined, detailed prompt and continue interactively.
- **Description**: This test begins with a prewritten conversation starter about making a hamburger, processes it with `StartConv()`, and then allows interactive input with `ContinueConv()`. It includes "exit" and "clear" commands for control.
- **Functionality**:
  - Displays a welcome message, the hamburger prompt, and the initial AI response.
  - Enters an interactive loop for further user input after the starter.
  - Handles "exit" and "clear" as in `test_cli.py`.
- **Expected Outcome**: The AI provides a response to the hamburger prompt (e.g., sauce substitutes, grilling tips), and subsequent inputs are handled contextually. The conversation resets with "clear" and ends with "exit" without errors.
- **Use Case**: Verifies the module’s capability to handle scripted conversation starters, useful for guided user experiences.

### 3. `prompt_with_input_test.py`
- **Purpose**: Evaluates the module's ability to incorporate user-provided input into a prewritten prompt and continue the conversation.
- **Description**: This test prompts the user for a dish name, constructs a detailed prompt with it (e.g., "Help me find ingredient substitutes for [dish]"), uses `StartConv()` to initiate, and then supports interactive input with `ContinueConv()`. It includes "exit" and "clear" commands.
- **Functionality**:
  - Requests a dish name from the user before starting.
  - Displays the constructed prompt and initial AI response.
  - Enters an interactive loop for follow-up inputs.
  - Handles "exit" and "clear" commands.
- **Expected Outcome**: The AI responds to the customized prompt with substitutes and suggestions for the user-specified dish, and the conversation continues logically. No crashes occur with valid input.
- **Use Case**: Tests dynamic prompt generation, essential for personalized interactions based on user input.

### 4. `off_topic_test.py`
- **Purpose**: Assesses the AI’s ability to steer the conversation back to cooking-related topics when faced with highly off-topic inputs, as mandated by the system prompt.
- **Description**: This test starts with a cooking prompt (pasta dish) using `StartConv()`, then sends a series of five diverse off-topic questions (e.g., Mars rover, Fortnite, Battle of Waterloo) with `ContinueConv()`, and finally allows interactive input. It includes "exit" and "clear" commands.
- **Functionality**:
  - Displays the cooking prompt and initial response.
  - Automatically tests five off-topic inputs and their responses.
  - Enters an interactive loop for further user input.
  - Handles "exit" and "clear" commands.
- **Expected Outcome**: The AI provides a relevant response to the pasta prompt and steers each off-topic question back to cooking (e.g., "Let’s focus on your pasta..."). The interactive phase functions normally afterward.
- **Use Case**: Ensures the AI adheres to its cooking-focused role, critical for maintaining the assistant’s intended behavior.

## Overall Test Suite Goals
- **Coverage**: The suite covers basic functionality (`test_cli.py`), scripted starts (`prompt_test.py`), dynamic prompts (`prompt_with_input_test.py`), and behavioral enforcement (`off_topic_test.py`).
- **Edge Cases**: Tests include resetting conversations, handling user input, and off-topic redirection, though additional edge cases (e.g., empty inputs, API failures) could be explored.
- **Maintainability**: Each test is self-contained, with clear output formatting, making it easy to debug and extend.

## Conclusion
This test suite provides a solid foundation for validating the `LLM.py` module’s functionality as a cooking assistant. Each test case targets a specific aspect, ensuring reliability and adherence to the system prompt. Future enhancements can build on this framework to address additional scenarios and improve robustness.