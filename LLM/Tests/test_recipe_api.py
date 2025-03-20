import unittest
import requests
import json
import sys
import os
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))
from app import app
from threading import Thread

class TestFlaskApp(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.app = app
        cls.server_thread = Thread(target=cls.app.run, kwargs={'host': '0.0.0.0', 'port': 5000, 'debug': False})
        cls.server_thread.daemon = True
        cls.server_thread.start()
        cls.base_url = 'http://127.0.0.1:5000'
        import time
        time.sleep(1)

    def test_1_clear_endpoint(self):
        response = requests.post(f'{self.base_url}/clear')
        self.assertEqual(response.status_code, 200)
        data = response.json()
        self.assertEqual(data['message'], 'Conversation history cleared')

    def test_2_start_endpoint_valid_input(self):
        payload = {
            "input": json.dumps({
                "recipe": "Chocolate Chip Cookies",
                "requiredIngredients": ["flour", "sugar", "butter", "chocolate chips"],
                "availableIngredients": ["flour", "butter", "chocolate chips"],
                "description": "Mix dry and wet ingredients, bake at 350°F for 12 minutes. Can use honey instead of sugar."
            })
        }
        response = requests.post(f'{self.base_url}/start', json=payload)
        self.assertEqual(response.status_code, 200)
        data = response.json()
        self.assertIn('response', data)
        response_str = data['response'].strip()
        if response_str.startswith('```json') and response_str.endswith('```'):
            response_str = response_str[7:-3].strip()
        response_text = json.loads(response_str)
        self.assertEqual(response_text['recipe'], 'Chocolate Chip Cookies')
        self.assertIn('missingIngredients', response_text)
        self.assertIn('steps', response_text)
        self.assertEqual(response_text['missingIngredients']['sugar'], 'honey')

    def test_3_start_endpoint_invalid_input(self):
        payload = {"input": ""}
        response = requests.post(f'{self.base_url}/start', json=payload)
        self.assertEqual(response.status_code, 200)
        data = response.json()
        self.assertIn('error', data)
        self.assertEqual(data['error'], 'Input cannot be empty. Please provide a valid recipe input.')

    def test_4_continue_endpoint_without_start(self):
        requests.post(f'{self.base_url}/clear')
        payload = {"input": "What about flour?"}
        response = requests.post(f'{self.base_url}/continue', json=payload)
        self.assertEqual(response.status_code, 200)
        data = response.json()
        self.assertIn('error', data)
        self.assertEqual(data['error'], 'Chat session not started. Use /start first.')

    def test_5_continue_endpoint_after_start(self):
        start_payload = {
            "input": json.dumps({
                "recipe": "Pancakes",
                "requiredIngredients": ["flour", "milk", "eggs"],
                "availableIngredients": ["flour", "milk"],
                "description": "Mix ingredients and cook on a skillet."
            })
        }
        requests.post(f'{self.base_url}/start', json=start_payload)
        continue_payload = {"input": "What can I use instead of eggs?"}
        response = requests.post(f'{self.base_url}/continue', json=continue_payload)
        self.assertEqual(response.status_code, 200)
        data = response.json()
        self.assertIn('response', data)
        self.assertTrue(isinstance(data['response'], str))

    @classmethod
    def tearDownClass(cls):
        pass

if __name__ == '__main__':
    unittest.main()