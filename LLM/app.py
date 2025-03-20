from flask import Flask, request, jsonify
#from flask_swagger import swagger
from flasgger import Swagger
import google.generativeai as genai

# Configure the API key for Google Generative AI
genai.configure(api_key="AIzaSyB6Z8R-k59SK3xoXL_vHEDAkhOyrh_XWHc")

# Define the system prompt for the conversational AI
system_prompt = """
This LLM generates detailed, step-by-step cooking instructions based on user-provided input. 

Users provide:
A recipe name
A list of required ingredients
A list of available ingredients
A brief description of the cooking process with possible ingredient alternatives

The AI will:
Generate a structured, numbered list of step-by-step instructions
Adjust the recipe based on the available ingredients
Suggest suitable ingredient substitutions if necessary

The AI does not engage in freeform conversation. It strictly follows the structured JSON input format and responds with a formatted JSON output containing the recipe instructions. do not use codeblock to generate the code snippet

Example response format is as follows:

{
  "recipe": "<recipe name>",
  "missingIngredients": {
    "<missing ingredient 1>": "<replacement ingredient 1>",
    "<missing ingredient 2>": "<replacement ingredient 2>",
    "...": "..."
    "<missing ingredient n>": "<replacement ingredient n>",
  },
  "steps": [
    "1. <step 1 instructions>",
    "2. <step 2 instructions>",
    "...",
    "n. <step n instructions>"
  ]
}

"""

# Initialize the model with the system instruction
model = genai.GenerativeModel("gemini-2.0-flash-exp", system_instruction=system_prompt)

# Global variable to maintain the chat session state across requests
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
swagger = Swagger(app)
# REST API endpoint to clear the conversation history
@app.route('/clear', methods=['POST'])
def clear_endpoint():
    """
    ---
    tags:
      - Conversation
    summary: Clear the conversation history
    description: Clears the conversation history by starting a new chat session with an empty history.
    responses:
      200:
        description: Success
        schema:
          type: object
          properties:
            message:
              type: string
              example: Conversation history cleared
    """
    result = Clear()
    return jsonify(result)

# REST API endpoint to start a new conversation
@app.route('/start', methods=['POST'])
def start_endpoint():
    """
    ---
    tags:
      - Conversation
    summary: Start a new conversation
    description: Starts a new conversation by clearing the history and sending the initial user input.
    parameters:
      - in: body
        name: body
        required: true
        schema:
          type: object
          properties:
            input:
              type: string
              description: The initial user message
              example: I’m missing sugar for cookies
    responses:
      200:
        description: Success
        schema:
          type: object
          properties:
            response:
              type: string
              description: The AI's response
      400:
        description: Bad Request
    """
    data = request.get_json()
    input_str = data.get('input', '')
    result = StartConv(input_str)
    return jsonify(result)

# REST API endpoint to continue the conversation
@app.route('/continue', methods=['POST'])
def continue_endpoint():
    """
    ---
    tags:
      - Conversation
    summary: Continue the conversation
    description: Continues the conversation by sending the user's input to the current chat session.
    parameters:
      - in: body
        name: body
        required: true
        schema:
          type: object
          properties:
            input:
              type: string
              description: The user's message
              example: What about flour?
    responses:
      200:
        description: Success
        schema:
          type: object
          properties:
            response:
              type: string
              description: The AI's response
      400:
        description: Bad Request
    """
    data = request.get_json()
    input_str = data.get('input', '')
    result = ContinueConv(input_str)
    return jsonify(result)


# Run the Flask app in debug mode
if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)