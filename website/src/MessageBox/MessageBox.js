import "./message.css"


function MessageBox({side, message}) {
    if (String(side) === "left") {
        return (
            <>
                <div className="left-triangle">
                    <img alt="" src="data/left-triangle.png" style={{ verticalAlign: 'top' }} />
                </div>
                <div className="message-box message-box-left">
                    {message.content}
                </div>
                <div style={{ clear: 'both' }} />
            </>
        );
    }
    if (String(side) === "right") {
        return (
            <>
                <div className="message-box message-box-right">
                    <div className="right-triangle">
                        <img alt="" src="data/right-triangle.png" style={{ verticalAlign: 'top' }} />
                    </div>
                    <div>
                        {message.content}
                    </div>
                </div>
                <div style={{ clear: 'both' }} />
            </>
        );
    }
    return (
        <>
        </>
    );
}

export default MessageBox;
