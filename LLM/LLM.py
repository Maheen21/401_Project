import google.generativeai as genai
import os

# Configure the API key securely
genai.configure(api_key="AIzaSyB6Z8R-k59SK3xoXL_vHEDAkhOyrh_XWHc")

model = genai.GenerativeModel("gemini-2.0-flash-exp")

# Initial system prompt
system_prompt = """
This GPT helps users find suitable ingredient substitutes when cooking or baking. Users input a missing ingredient and the dish they are preparing, and the AI suggests alternatives based on flavor profiles, nutritional value, and dietary restrictions. 

It also provides recipe ideas based on available ingredients and can identify missing ingredients while suggesting substitutes. Users can optionally specify that they do not want to shop for new ingredients, in which case the AI will only suggest recipes using what they have. 

If the user goes off-topic, the AI will gently steer the conversation back to ingredient substitutions, recipes, or cooking-related discussions.
"""

conversation_history = system_prompt  # Stores the chat context

print("[✨ Missing Ingredient Finder Chat ✨]")
print("Type 'exit' to end the chat, or 'clear' to reset the conversation.\n")

while True:
    user_input = input("You: ").strip()
    
    if user_input.lower() == "exit":
        print("✨ Goodbye! Happy cooking! ✨")
        break
    elif user_input.lower() == "clear":
        conversation_history = system_prompt  # Reset chat history
        print("\n✨ Conversation cleared! Start fresh. ✨\n")
        continue
    
    # Append user input to conversation history
    conversation_history += f"\nUser: {user_input}\n"

    # Generate response with conversation context
    response = model.generate_content(conversation_history)

    if hasattr(response, 'text'):
        print("\n[✨ Missing Ingredient Finder ✨]\n" + response.text + "\n")
        conversation_history += f"\nAI: {response.text}\n"  # Maintain history
    else:
        print("Error: No response received from AI.\n")
