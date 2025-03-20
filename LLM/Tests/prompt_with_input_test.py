import sys
import os
sys.path.append(os.path.dirname(os.path.dirname(__file__)))

from LLM import Clear, StartConv, ContinueConv

# Prompt the user for initial input to customize the conversation starter
user_dish = input("Please enter the dish you need ingredient substitutes for (e.g., 'lasagna', 'cake'): ").strip()

# Construct the prewritten prompt with user input
prewritten_prompt = f"""
Help me find ingredient substitutes for [{user_dish}]. I’m preparing this dish but have run into a few missing items in my kitchen. I have a variety of basic ingredients like flour, eggs, milk, butter, cheese, and some vegetables, but I’m missing a few key components. I’d prefer not to shop for new ingredients, so please suggest substitutes using what I already have. Additionally, I’d like tips on adjusting the recipe to maintain flavor and texture with these substitutions. If possible, offer a creative twist to enhance the dish with my available ingredients. I’m also curious about a quick side dish that pairs well with [{user_dish}] and can be made in under 15 minutes. What do you recommend?
"""

# Print welcome message and indicate the conversation has started
print("[✨ Missing Ingredient Finder Chat ✨]\n\n")
print("The conversation has started with the following prompt:\n")
print("[🙂 User 🙂]\n\n" + prewritten_prompt + "\n")

# Start the conversation with the constructed prompt
initial_response = StartConv(prewritten_prompt)
print("[✨ Missing Ingredient Finder ✨]\n\n" + initial_response + "\n")
print("You can now continue the conversation. Type 'exit' to end the chat, or 'clear' to reset the conversation.\n")

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