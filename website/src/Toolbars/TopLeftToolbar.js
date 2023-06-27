import "./toolbar.css"


function TopLeftToolbar({self_img, addContact,self_name}) {
    const onContactAdd = () => {
        const input = document.getElementById("inputUsername");
        if (input.value === "") {
            alert("Please enter a username!");
        } else if (!addContact({name: input.value})) {
            alert("Contact already exists!");
        }
        input.value = "";
    }

    return (
        <div id="top-left-toolbar" className="toolbar">
            <div className="avatar">
                <img id="profilePicture" alt="" src={self_img} />
            </div>
            <h3 id="self-name">{ self_name}</h3>
            <button id="contact-button" type="button" className="btn" data-bs-toggle="modal" data-bs-target="#contactModal">
                <img src="data/add-contact.png" alt="contacts" style={{ width: '100%' }} />
            </button>
            <div className="modal fade" id="contactModal" tabIndex={-1} aria-labelledby="contactModalLabel" aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h1 className="modal-title fs-5" id="contactModalLabel">Add contact</h1>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" />
                        </div>
                        <div className="modal-body">
                            <input type="text" className="form-control form-input" id="inputUsername" />
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="button" className="btn btn-primary" data-bs-dismiss="modal" onClick={onContactAdd}>Add Contact</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default TopLeftToolbar;