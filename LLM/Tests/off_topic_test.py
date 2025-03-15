import sys
import os
sys.path.append(os.path.dirname(os.path.dirname(__file__)))

from LLM import Clear, StartConv, ContinueConv

# Define the initial cooking-related conversation starter
cooking_prompt = """
I’m planning to make a pasta dish for dinner tonight. I have pasta, tomatoes, garlic, and olive oil, but I’m out of parmesan cheese. I’d prefer not to shop, so please suggest a substitute using what I have. Any tips for enhancing the flavor?
"""

# List of highly off-topic inputs to test steering
off_topic_inputs = [
    "Can you tell me about the latest Mars rover mission?",
    "What’s the best strategy for winning at Fortnite?",
    "Who won the Battle of Waterloo, and why did it matter?",
    "How do I improve my guitar playing skills?",
    "What are the current trends in cryptocurrency trading?"
]

# Print welcome message and indicate the conversation has started
print("[✨ Missing Ingredient Finder Chat ✨]\n\n")
print("The conversation has started with the following prompt:\n")
print("[🙂 User 🙂]\n\n" + cooking_prompt + "\n")

# Start the conversation with the cooking prompt
initial_response = StartConv(cooking_prompt)
print("[✨ Missing Ingredient Finder ✨]\n\n" + initial_response + "\n")

# Test multiple off-topic inputs
print("Testing off-topic steering with a variety of unrelated questions:\n")
for off_topic_input in off_topic_inputs:
    print("[🙂 User 🙂]\n\n" + off_topic_input + "\n")
    off_topic_response = ContinueConv(off_topic_input)
    print("[✨ Missing Ingredient Finder ✨]\n\n" + off_topic_response + "\n")

print("Off-topic tests complete. You can now continue the conversation. Type 'exit' to end the chat, or 'clear' to reset the conversation.\n")

# Main interaction loop
while True:
    user_input = input("[🙂 User 🙂]\n\n").strip()
    
    # Handle exit command
    if user_input.lower() == "exit":
        print("✨ Goodbye! Happy cooking! ✨")
        break
    
    # Handle clear command
    elif user_input.lower() == "clear":
        Clear()
        print("\n✨ Conversation cleared! Start fresh. ✨\n")
        continue
    
    # Process user input and print AI response
    response = ContinueConv(user_input)
    print("\n[✨ Missing Ingredient Finder ✨]\n\n" + response + "\n")