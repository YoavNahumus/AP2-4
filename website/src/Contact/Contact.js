import "./Contact.css"
import "../chat.css"

function Contact({user,lastMessage,selectContact}) {
	return (
		<div className="contact" onClick={selectContact}>
			<div className="avatar">
				<img alt="" src={user.profilePic} />
			</div>
			<div className="meta">
				<h3 className="name">
					{user.displayName}
				</h3>
				<p>
					{/* {msg_from}{msg_from === "" ? "" : ":"} {last_msg} */}
				</p>
				<span className="time">
					{/* {msg_time} */}
				</span>
			</div>
		</div>
	);
}

export default Contact;