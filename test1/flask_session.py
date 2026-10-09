import os

from flask import Flask, request, jsonify, send_from_directory
from openai import OpenAI

app = Flask(__name__)
client = OpenAI()

STATIC_DIR = os.path.join(os.path.dirname(__file__), "static")
sessions = {}


@app.after_request
def add_cors(response):
    response.headers["Access-Control-Allow-Origin"] = "*"
    response.headers["Access-Control-Allow-Headers"] = "Content-Type"
    response.headers["Access-Control-Allow-Methods"] = "GET, POST, OPTIONS"
    return response


@app.route("/")
def index():
    return send_from_directory(STATIC_DIR, "index.html")


@app.route("/chat", methods=["POST", "OPTIONS"])
def chat():
    if request.method == "OPTIONS":
        return "", 204
    data = request.json

    session_id = data["session_id"]
    user_input = data["input"]

    # 获取历史对话
    history = sessions.get(session_id, [])

    history.append({
        "role": "user",
        "content": user_input
    })

    response = client.responses.create(
        model="gpt-6-astra",
        input=history
    )

    answer = response.output_text

    history.append({
        "role": "assistant",
        "content": answer
    })

    sessions[session_id] = history

    return jsonify({
        "session_id": session_id,
        "output": answer
    })


app.run(host="0.0.0.0", port=5000)
