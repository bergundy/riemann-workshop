import requests

from flask import Flask
from monitor import monitor

app = Flask(__name__)

@app.route('/')
def handle_incident():
    with monitor('GET /'):
        response = requests.get('http://localhost:8000')
        return response.text


if __name__ == '__main__':
    app.run(port=8001)
