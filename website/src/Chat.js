import "./main.css"
import "./forms.css"
import "./chat.css"
import Contact from "./Contact/Contact";

import ChatToolbar from "./Toolbars/ChatToolbar";
import TopRightToolbar from "./Toolbars/TopRightToolbar";
import TopLeftToolbar from "./Toolbars/TopLeftToolbar";
import MessageBox from "./MessageBox/MessageBox";
import { useState } from "react";
import CurrentUser from "./Users/CurrentUser";
import { useNavigate } from "react-router-dom";
import token from "./Users/CurrentToken";
import {io} from "socket.io-client"

function Chat() {

    var socket;

    const navigate = useNavigate();

    const [selectedContact, setSelectedContact] = useState("");

    const [messageList, setMessageList] = useState([]);

    const [picture, setPicture] = useState(null);

    const [contactList, setContactList] = useState([]);

    const logout = (e) => {
        CurrentUser.user = {};
        token.key = "";     
        if (socket) {
            socket.disconnect();
        }
        navigate("/");
	}

    async function onLoad() {
        if (token.key === "" || token.key === null) {
            navigate("/");
        }
        if (CurrentUser.user === null || CurrentUser.user === undefined || CurrentUser.user === "") {
            navigate("/");
        }
        const res2 = await fetch(`http://localhost:5000/api/Users/${CurrentUser.user}`, {
            'method': 'get',
            'headers': {
                'Authorization': `Bearer ${token.key}`,
                'Content-Type': 'application/json'
            }
        });
        if (res2.status !== 200) {
            console.log("we have a problem!");
        }
        const data = await res2.json();
        CurrentUser.displayName = data.displayName;
        setPicture(data.profilePic);
        CurrentUser.picture = data.profilePic;
        updateContactList();

        socket = io("ws://localhost:5001");

        socket.on("hello", (arg) => {
            console.log("connected");
        });

        socket.emit('token', token.key);

        socket.on("invalidToken", (arg) => {
            logout();
        });
    
        socket.on("message", (arg) => {
            if (selectedContact.id != null) {
                updateMessageList(selectedContact.id);
            }
        });
    
        socket.on("updateChats", (arg) => {
            updateContactList();
        });
    }


    
    function selectContact(contact) {
        setSelectedContact(contact);
        updateMessageList(contact.id);
    }

    async function addContact({ name }) {
        var data = {
            "username" : name   
        }
        data = JSON.stringify(data);
        const res = await fetch("http://localhost:5000/api/Chats", {
            'method': 'post',
            'headers': {
                'Authorization': `Bearer ${token.key}`,
                'Content-Type': 'application/json'
            },
            'body': data
        });

        if (res.status === 200) {
            updateContactList()
            return true;
        }
        return false;
    }

    async function appendMessage(text) {
        if (text === '' || text === null || text === undefined) {
            return;
        }
        if (selectedContact === '' || selectedContact === null || selectedContact === undefined) {
            return;
        }
        var data = {
            "msg" : text   
        }
        data = JSON.stringify(data);
        const res = await fetch(`http://localhost:5000/api/Chats/${selectedContact.id}/Messages`, {
            'method': 'post',
            'headers': {
                'Authorization': `Bearer ${token.key}`,
                'Content-Type': 'application/json'
            },
            'body': data
        })

        if (res.status != 200 && res.status != 204) {
            console.log("error sending msg!");
            return false;
        }
        updateMessageList(selectedContact.id);
        return true;

    }

    async function updateMessageList(id) {
        const res = await fetch(`http://localhost:5000/api/Chats/${id}/Messages`, {
            'method': 'get',
            'headers': {
                'Authorization': `Bearer ${token.key}`,
                'Content-Type': 'application/json'
            }
        })
        const data = await res.json();
        // const newMessageList = data.slice(0).reverse().map((message, key) => {
            const newMessageList = data.map((message, key) => {
            if (message.sender.username === CurrentUser.user) {
                return <MessageBox side={"right"} message={message} key={key} />
            }
            return <MessageBox side={"left"} message={message} key={key} />
        });    
        // const newMessageList =
        setMessageList(newMessageList);
    }

    async function updateContactList() {
        const res = await fetch("http://localhost:5000/api/Chats", {
            'method': 'get',
            'headers': {
                'Authorization': `Bearer ${token.key}`,
                'Content-Type': 'application/json'
            }
        }) 
        const data = await res.json();
        const newContactList = data.map((contact, key) => {
            return <Contact user={contact.user} key={key} selectContact={e => selectContact(contact)} />
        })
        setContactList(newContactList);
    }
          
	return (
        <div className="Chat" onLoad={onLoad}>
            <div>
                <div className="green-top-bar" />
                <div className="background" />
                <button type="button" className="btn btn-danger logout-button" to="/" onClick={logout}>Logout</button>
                <div className="middle-div no-padding">
                    <div id="left-bar">
                        <TopLeftToolbar self_img={picture} addContact={addContact} self_name={CurrentUser.displayName} />
                        <div className="contact-list">
                            {contactList}
                        </div>
                    </div>
                    <div id="right-bar">
                        <TopRightToolbar selected_contact={selectedContact} />
                        <div id="chat">
                            {messageList}
                        </div>
                        <ChatToolbar onSend={appendMessage} />
                    </div>
                </div>
            </div>
        </div>
	);
}

export default Chat;