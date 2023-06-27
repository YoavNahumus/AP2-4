import "./toolbar.css"


function ChatToolbar({ onSend }) {
    const send = () => {
        const input = document.getElementById("input-text");
        const text = input.value;
        input.value = "";
        if (text === "") return;
        onSend(text);
    }

    function handleKeyDown(e) {
        if (e.key === 'Enter') {
            send();
            e.preventDefault();
        }
    }

    return (
        <div className="toolbar" id="chat-toolbar">
            <input type="text" className="form-control form-input focus-ring-primary" id="input-text" onKeyDown={handleKeyDown} />
            <button id="send-button" className="btn" onClick={send}>
                <img src="data/send.png" alt="contacts" style={{ width: '100%' }} />
            </button>
        </div>
    );
}

export default ChatToolbar