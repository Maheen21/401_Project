import google.generativeai as genai

# Configure the API key
genai.configure(api_key="AIzaSyB6Z8R-k59SK3xoXL_vHEDAkhOyrh_XWHc")
# I know it is not secure to store the API Key in the code but I couldn't find any way to do it because using firebase would mean that I would need to store a firebase API key in the code which is potentially even more dangerous


# Define the system prompt
system_prompt = """
This GPT helps users find suitable ingredient substitutes when cooking or baking. Users input a missing ingredient and the dish they are preparing, and the AI suggests alternatives based on flavor profiles, nutritional value, and dietary restrictions. 

It also provides recipe ideas based on available ingredients and can identify missing ingredients while suggesting substitutes. Users can optionally specify that they do not want to shop for new ingredients, in which case the AI will only suggest recipes using what they have. 

If the user goes off-topic, the AI will gently steer the conversation back to ingredient substitutions, recipes, or cooking-related discussions. \n\n
"""

# Initialize the model with the system instruction
model = genai.GenerativeModel("gemini-2.0-flash-exp", system_instruction=system_prompt)

# Global chat session variable to maintain state across function calls
_chat = None

def Clear():
    """
    Clears the conversation history by starting a new chat session with an empty history.
    """
    global _chat
    _chat = model.start_chat(history=[])

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