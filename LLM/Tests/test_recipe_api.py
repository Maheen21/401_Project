import pytest
import requests
import json

BASE_URL = "http://127.0.0.1:5000"

@pytest.fixture
def api_client():
    yield
    requests.post(f"{BASE_URL}/clear")

def test_clear_endpoint():
    response = requests.post(f"{BASE_URL}/clear")
    assert response.status_code == 200
    assert response.json() == {"message": "Conversation history cleared"}

def test_start_endpoint_valid_input(api_client):
    payload = {
        "input": json.dumps({
            "recipe": "Chocolate Chip Cookies",
            "requiredIngredients": ["flour", "sugar", "butter", "chocolate chips", "eggs"],
            "availableIngredients": ["flour", "butter", "chocolate chips", "eggs"],
            "description": "Mix ingredients, form dough, bake at 350°F for 10-12 minutes. Sugar can be replaced with honey."
        })
    }
    response = requests.post(f"{BASE_URL}/start", json=payload)
    assert response.status_code == 200
    data = json.loads(response.json()["response"])
    assert data["recipe"] == "Chocolate Chip Cookies"
    assert "sugar" in data["missingIngredients"]
    assert data["missingIngredients"]["sugar"] == "honey"
    assert len(data["steps"]) > 0
    assert data["steps"][0].startswith("1.")

def test_start_endpoint_missing_input(api_client):
    payload = {"input": ""}
    response = requests.post(f"{BASE_URL}/start", json=payload)
    assert response.status_code == 200
    assert response.json() == {"error": "Input cannot be empty. Please provide recipe details."}

def test_continue_endpoint_no_session(api_client):
    payload = {"input": "What about flour?"}
    response = requests.post(f"{BASE_URL}/continue", json=payload)
    assert response.status_code == 200
    assert response.json() == {"error": "Chat session not started. Use /start first."}

def test_full_conversation_flow(api_client):
    start_payload = {
        "input": json.dumps({
            "recipe": "Pancakes",
            "requiredIngredients": ["flour", "milk", "eggs", "sugar"],
            "availableIngredients": ["flour", "eggs"],
            "description": "Mix ingredients into a batter, cook on a skillet. Milk can be replaced with water, sugar with syrup."
        })
    }
    start_response = requests.post(f"{BASE_URL}/start", json=start_payload)
    assert start_response.status_code == 200
    start_data = json.loads(start_response.json()["response"])
    assert start_data["recipe"] == "Pancakes"
    assert "milk" in start_data["missingIngredients"]
    assert "sugar" in start_data["missingIngredients"]

    continue_payload = {"input": "Can I use almond milk instead of water?"}
    continue_response = requests.post(f"{BASE_URL}/continue", json=continue_payload)
    assert continue_response.status_code == 200
    continue_data = json.loads(continue_response.json()["response"])
    assert "steps" in continue_data