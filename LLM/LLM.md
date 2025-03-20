# LLM Directory Documentation

## Overview

The LLM directory contains the necessary files to run a Flask-based API that utilizes Google's Generative AI to assist users with ingredient substitutions and recipe suggestions. This documentation provides an overview of the files and how to set up and run the application.

## Files in the LLM Directory

1.  **Dockerfile**

    * This file is used to containerize the application.
    * It provides the instructions for building a Docker image that includes the necessary dependencies and environment configurations to run the API.

2.  **app.py**

    * The core application file that defines a Flask API for handling ingredient substitution queries.
    * The API integrates with Google's Generative AI (Gemini-2.0) and provides the following endpoints:
        * `/clear` (POST): Clears the conversation history.
        * `/start` (POST): Starts a new conversation with an initial input.
        * `/continue` (POST): Continues an existing conversation with the AI.
        * `/spec` (GET): Provides the Swagger API documentation.

3.  **requirements.txt**

    * Lists the dependencies required to run the application, including:
        * `flask` – For the web framework.
        * `flask-swagger` – For API documentation.
        * `google-generativeai` – To interact with Google's AI model.

## Setup and Installation

### Prerequisites

* Docker (if running in a containerized environment)
* Python 3
* Virtual environment (optional, but recommended)

### Running the Application

1.  **Using Python Locally**

    * (Instructions to be added when available)

2.  **Using Docker**

    * (Instructions to be added when available)

## API Endpoints

| Endpoint   | Method | Description                               |
| ---------- | ------ | ----------------------------------------- |
| `/clear`   | POST   | Clears the conversation history.        |
| `/start`   | POST   | Starts a new conversation with an initial input. |
| `/continue` | POST   | Continues an ongoing conversation.          |
| `/spec`    | GET    | Provides Swagger API documentation.      |

## Notes

* Ensure you set the Google Generative AI API key before running the application.
* The application is designed to assist with ingredient substitutions and recipe suggestions, keeping conversations focused on cooking-related topics.
* The `app.py` file initializes the AI model and manages user interactions via RESTful API endpoints.
