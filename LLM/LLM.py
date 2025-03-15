import google.generativeai as genai
import os

# Configure the API key securely
genai.configure(api_key="AIzaSyB6Z8R-k59SK3xoXL_vHEDAkhOyrh_XWHc")

# Define the system prompt
system_prompt = """
This GPT helps users find suitable ingredient substitutes when cooking or baking. Users input a missing ingredient and the dish they are preparing, and the AI suggests alternatives based on flavor profiles, nutritional value, and dietary restrictions. 

It also provides recipe ideas based on available ingredients and can identify missing ingredients while suggesting substitutes. Users can optionally specify that they do not want to shop for new ingredients, in which case the AI will only suggest recipes using what they have. 

If the user goes off-topic, the AI will gently steer the conversation back to ingredient substitutions, recipes, or cooking-related discussions. \n\n
"""

# Initialize the model with the system instruction
model = genai.GenerativeModel("gemini-2.0-flash-exp", system_instruction=system_prompt)

# Start a chat session with an empty history
chat = model.start_chat(history=[])

# Print welcome message and instructions
print("[✨ Missing Ingredient Finder Chat ✨]:\n\n")
print("Type 'exit' to end the chat, or 'clear' to reset the conversation.\n")

# Main interaction loop
while True:
    user_input = input("[🙂 User 🙂]:\n\n").strip()
    
    # Handle exit command
    if user_input.lower() == "exit":
        print("✨ Goodbye! Happy cooking! ✨")
        break
    
    # Handle clear command
    elif user_input.lower() == "clear":
        # Reset the conversation by starting a new chat session with empty history
        chat = model.start_chat(history=[])
        print("\n✨ Conversation cleared! Start fresh. ✨\n")
        continue
    
    # Send the user's input to the chat session
    response = chat.send_message(user_input)
    
    # Check if the response has a text attribute and print it
    if hasattr(response, 'text'):
        print("\n[✨ Missing Ingredient Finder ✨]\n\n" + response.text + "\n")
    else:
        print("Error: No response received from AI.\n")