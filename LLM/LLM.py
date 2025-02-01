import google.generativeai as genai
import os


genai.configure(api_key="AIzaSyB6Z8R-k59SK3xoXL_vHEDAkhOyrh_XWHc")


model = genai.GenerativeModel("gemini-2.0-flash-exp")

# System instruction for consistent behavior
system_prompt = """
This GPT helps users find suitable ingredient substitutes when cooking or baking. Users input a missing ingredient and the dish they are preparing, and the AI suggests alternatives based on flavor profiles, nutritional value, and dietary restrictions. 

It also provides recipe ideas based on available ingredients and can identify missing ingredients while suggesting substitutes. Users can optionally specify that they do not want to shop for new ingredients, in which case the AI will only suggest recipes using what they have. 

If the user goes off-topic, the AI will gently steer the conversation back to ingredient substitutions, recipes, or cooking-related discussions. 
"""

print("[✨ Missing Ingredient Finder Chat ✨]")
print("Type 'exit' to end the chat.\n")

while True:
    user_input = input("You: ").strip()
    
    if user_input.lower() == "exit":
        print("✨ Goodbye! Happy cooking! ✨")
        break
    
    response = model.generate_content(system_prompt + "\nUser: " + user_input)
    
    if hasattr(response, 'text'):
        print("\n[✨ Missing Ingredient Finder ✨]\n" + response.text + "\n")
    else:
        print("Error: No response received from AI.\n")
