# LLM.py Documentation

## Overview
The `LLM.py` module provides a conversational AI assistant focused on helping users find ingredient substitutes and suggesting recipes. It utilizes the Google Generative AI API to generate responses while maintaining a structured interaction with users.

## Dependencies
- `google.generativeai` (Google Generative AI API)

## Configuration
The module requires an API key for Google Generative AI, which is currently hardcoded in the script (not secure). The system prompt is pre-defined to keep conversations related to cooking and ingredient substitution.

## System Prompt
The assistant is programmed with the following core functionality:
- Suggest ingredient substitutes based on user input.
- Provide recipe ideas with available ingredients.
- Identify missing ingredients and recommend alternatives.
- Keep the conversation focused on cooking and steer away from off-topic discussions.

## Global Variables
- `model`: An instance of `GenerativeModel` configured with the system prompt.
- `_chat`: A global variable storing the active chat session.

## Functions
### `Clear()`
```python
def Clear():
    """
    Clears the conversation history by starting a new chat session with an empty history.
    """
    global _chat
    _chat = model.start_chat(history=[])
```
- **Purpose**: Resets the conversation by clearing the chat history.
- **Usage**: Called when a new conversation session needs to be initiated.

### `StartConv(input_str: str) -> str`
```python
def StartConv(input_str):
    """
    Starts a new conversation by clearing the history and sending the initial user input.

    Args:
        input_str (str): The initial user message to start the conversation.

    Returns:
        str: The AI's response to the initial message.
    """
    Clear()
    response = _chat.send_message(input_str)
    if hasattr(response, 'text'):
        return response.text
    else:
        return "Error: No response received from AI."
```
- **Purpose**: Initializes a new conversation with the provided input.
- **Arguments**:
  - `input_str` (str): The initial user message.
- **Returns**: AI-generated response as a string.
- **Usage**: Used when starting a fresh conversation.

### `ContinueConv(input_str: str) -> str`
```python
def ContinueConv(input_str):
    """
    Continues the conversation by sending the user's input to the current chat session.

    Args:
        input_str (str): The user's message to continue the conversation.

    Returns:
        str: The AI's response to the user's message, or an error if the session isn't started.
    """
    if _chat is None:
        return "Error: Chat session not started. Please use StartConv first."
    response = _chat.send_message(input_str)
    if hasattr(response, 'text'):
        return response.text
    else:
        return "Error: No response received from AI."
```
- **Purpose**: Sends a user message in an ongoing conversation session.
- **Arguments**:
  - `input_str` (str): User's message to continue the conversation.
- **Returns**: AI-generated response as a string.
- **Error Handling**: Returns an error if the chat session is not started.
- **Usage**: Used to maintain an active chat session after initialization.

## Usage Example
```python
from LLM import StartConv, ContinueConv, Clear

# Start a conversation
response = StartConv("I am making a cake but I am out of eggs. What can I use instead?")
print(response)

# Continue the conversation
response = ContinueConv("I also don’t have milk. Any substitutes?")
print(response)
```

## Test Cases
The module has been tested using the following scripts:

- **`test_cli.py`**: Ensures the module works as a command-line interface (CLI) tool.
- **`prompt_test.py`**: Verifies that the AI responds correctly to a pre-written cooking-related prompt.
- **`prompt_with_input_test.py`**: Confirms that user-provided dish names are correctly incorporated into the conversation.
- **`off_topic_test.py`**: Ensures the AI keeps conversations focused on cooking, even when given off-topic input.

## Limitations
- The API key is hardcoded, which is a security risk.
- No exception handling for API failures or network errors.
- Limited to cooking-related conversations and unable to handle multi-topic discussions efficiently.

## Conclusion
The `LLM.py` module effectively serves as an ingredient substitution assistant by leveraging Google's Generative AI. While functional, it can be improved in terms of security, robustness, and flexibility.

