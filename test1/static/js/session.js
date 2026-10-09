const STORAGE_KEY = "chat_session_id";

export function createSessionId() {
  return `sess_${Date.now().toString(36)}_${Math.random().toString(36).slice(2, 8)}`;
}

export function getSessionId() {
  const stored = localStorage.getItem(STORAGE_KEY);
  if (stored) {
    return stored;
  }
  const next = createSessionId();
  localStorage.setItem(STORAGE_KEY, next);
  return next;
}

export function setSessionId(sessionId) {
  const value = sessionId.trim() || createSessionId();
  localStorage.setItem(STORAGE_KEY, value);
  return value;
}
