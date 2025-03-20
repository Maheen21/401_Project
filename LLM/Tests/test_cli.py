import sys
import os
sys.path.append(os.path.dirname(os.path.dirname(__file__)))

from LLM import Clear, ContinueConv

# Initialize the chat session
Clear()

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
        Clear()
        print("\n✨ Conversation cleared! Start fresh. ✨\n")
        continue
    
    # Process user input and print AI response
    response = ContinueConv(user_input)
    print("\n[✨ Missing Ingredient Finder ✨]\n\n" + response + "\n")