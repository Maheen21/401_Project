from flask import Flask, request, jsonify
import google.generativeai as genai

# Configure the API key for Google Generative AI
# Note: For security, consider storing this in an environment variable (e.g., using os.environ).
# As per your request, it’s included directly here since you couldn’t find an alternative.
genai.configure(api_key="AIzaSyB6Z8R-k59SK3xoXL_vHEDAkhOyrh_XWHc")

# Define the system prompt for the conversational AI
system_prompt = """
This GPT helps users find suitable ingredient substitutes when cooking or baking. Users input a missing ingredient and the dish they are preparing, and the AI suggests alternatives based on flavor profiles, nutritional value, and dietary restrictions. 

It also provides recipe ideas based on available ingredients and can identify missing ingredients while suggesting substitutes. Users can optionally specify that they do not want to shop for new ingredients, in which case the AI will only suggest recipes using what they have. 

If the user goes off-topic, the AI will gently steer the conversation back to ingredient substitutions, recipes, or cooking-related discussions.
"""

# Initialize the model with the system instruction
model = genai.GenerativeModel("gemini-2.0-flash-exp", system_instruction=system_prompt)

# Global variable to maintain the chat session state across requests
# Note: This works for development but may need a more robust solution (e.g., session storage) for production.
_chat = None

# Function to clear the conversation history
def Clear():
    """
    Clears the conversation history by starting a new chat session with an empty history.
    """
    global _chat
    _chat = model.start_chat(history=[])
    return {"message": "Conversation history cleared"}

# Function to start a new conversation
def StartConv(input_str):
    """
    Starts a new conversation by clearing the history and sending the initial user input.

    Args:
        input_str (str): The initial user message to start the conversation.

    Returns:
        dict: A dictionary containing the AI's response or an error message.
    """
    Clear()  # Clear the history before starting a new conversation
    response = _chat.send_message(input_str)
    if hasattr(response, 'text'):
        return {"response": response.text}
    else:
        return {"error": "No response received from AI."}

# Function to continue the conversation
def ContinueConv(input_str):
    """
    Continues the conversation by sending the user's input to the current chat session.

    Args:
        input_str (str): The user's message to continue the conversation.

    Returns:
        dict: A dictionary containing the AI's response or an error message.
    """
    global _chat
    if _chat is None:
        return {"error": "Chat session not started. Use /start first."}
    response = _chat.send_message(input_str)
    if hasattr(response, 'text'):
        return {"response": response.text}
    else:
        return {"error": "No response received from AI."}

# Initialize the Flask application
app = Flask(__name__)

# REST API endpoint to clear the conversation history
@app.route('/clear', methods=['POST'])
def clear_endpoint():
    """
    Endpoint to clear the conversation history.
    Expects a POST request (no body required).
    Example: curl -X POST http://127.0.0.1:5000/clear
    """
    result = Clear()
    return jsonify(result)

# REST API endpoint to start a new conversation
@app.route('/start', methods=['POST'])
def start_endpoint():
    """
    Endpoint to start a new conversation.
    Expects a POST request with JSON body containing 'input' field.
    Example: curl -X POST -H "Content-Type: application/json" -d '{"input": "I’m missing sugar for cookies"}' http://127.0.0.1:5000/start
    """
    data = request.get_json()
    input_str = data.get('input', '')
    result = StartConv(input_str)
    return jsonify(result)

# REST API endpoint to continue the conversation
@app.route('/continue', methods=['POST'])
def continue_endpoint():
    """
    Endpoint to continue the conversation.
    Expects a POST request with JSON body containing 'input' field.
    Example: curl -X POST -H "Content-Type: application/json" -d '{"input": "What about flour?"}' http://127.0.0.1:5000/continue
    """
    data = request.get_json()
    input_str = data.get('input', '')
    result = ContinueConv(input_str)
    return jsonify(result)

# Run the Flask app in debug mode
if __name__ == '__main__':
    app.run(debug=True)