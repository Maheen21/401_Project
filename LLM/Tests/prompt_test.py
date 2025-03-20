import sys
import os
sys.path.append(os.path.dirname(os.path.dirname(__file__)))

from LLM import Clear, StartConv, ContinueConv

# Define the detailed conversation starter prompt
conversation_starter = """
I'm planning to make a classic beef hamburger for dinner tonight. I have ground beef, buns, lettuce, tomato, and cheese in my kitchen, which should cover the basics. However, I just noticed I'm out of ketchup and mustard, which I typically mix together for a simple sauce. I do have some pickles and onions in the fridge, and I’m wondering if I can use them to create a substitute sauce with what I have on hand. I’d prefer not to run to the store, so please suggest something using only my current ingredients. Also, I’m grilling the patties outdoors and want them to be juicy yet fully cooked—any tips for getting that perfect balance? I’m curious about adding a unique topping to elevate the flavor, maybe something unexpected but delicious. Oh, and I have a spice rack with basics like salt, pepper, and garlic powder if that helps with seasoning or sauce ideas. Lastly, I’d love a quick and easy side dish recommendation to pair with the burger, ideally something I can whip up in under 20 minutes. What do you suggest?
"""

# Print welcome message and indicate the conversation has started
print("[✨ Missing Ingredient Finder Chat ✨]\n\n")
print("The conversation has started with the following prompt:\n")
print("[🙂 User 🙂]\n\n" + conversation_starter + "\n")

# Start the conversation with the predefined prompt
initial_response = StartConv(conversation_starter)
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