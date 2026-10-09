import { sendChat } from "./api.js";
import { createSessionId, getSessionId, setSessionId } from "./session.js";

const sessionInput = document.getElementById("session-id");
const newSessionBtn = document.getElementById("new-session");
const messagesEl = document.getElementById("messages");
const form = document.getElementById("chat-form");
const inputEl = document.getElementById("user-input");
const sendBtn = document.getElementById("send-btn");

sessionInput.value = getSessionId();

function appendBubble(role, text) {
  const bubble = document.createElement("div");
  bubble.className = `bubble ${role}`;
  bubble.textContent = text;
  messagesEl.appendChild(bubble);
  messagesEl.scrollTop = messagesEl.scrollHeight;
}

newSessionBtn.addEventListener("click", () => {
  sessionInput.value = setSessionId(createSessionId());
  messagesEl.innerHTML = "";
  appendBubble("assistant", "已开始新会话。");
});

sessionInput.addEventListener("change", () => {
  sessionInput.value = setSessionId(sessionInput.value);
});

form.addEventListener("submit", async (event) => {
  event.preventDefault();
  const input = inputEl.value.trim();
  if (!input) {
    return;
  }

  const sessionId = setSessionId(sessionInput.value);
  sessionInput.value = sessionId;

  appendBubble("user", input);
  inputEl.value = "";
  sendBtn.disabled = true;

  try {
    const data = await sendChat(sessionId, input);
    appendBubble("assistant", data.output || "(空回复)");
  } catch (error) {
    appendBubble("error", error.message);
  } finally {
    sendBtn.disabled = false;
    inputEl.focus();
  }
});

inputEl.addEventListener("keydown", (event) => {
  if (event.key === "Enter" && !event.shiftKey) {
    event.preventDefault();
    form.requestSubmit();
  }
});
