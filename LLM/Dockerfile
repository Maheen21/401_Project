# Use the official Python 3.9 image as the base image
FROM python:3.9

# Set the working directory inside the container to /app
WORKDIR /app

# Copy the requirements.txt file into the working directory
COPY requirements.txt .

# Install the Python dependencies listed in requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# Copy all files from the current directory into the working directory
COPY . .

# Set the FLASK_APP environment variable to point to your application file
ENV FLASK_APP=app.py

# Enable debug mode for Flask, matching your app.run(debug=True)
ENV FLASK_DEBUG=1

# Document that the container listens on port 5000
EXPOSE 5000

# Run the Flask application, binding to all interfaces (0.0.0.0) on port 5000
CMD ["flask", "run", "--host=0.0.0.0"]