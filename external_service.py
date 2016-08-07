from flask import Flask

app = Flask(__name__)

@app.route('/')
def handle_incident():
    return 'Hello from external service'


if __name__ == '__main__':
    app.run(port=8000)
