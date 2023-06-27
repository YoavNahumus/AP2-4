import "./toolbar.css"


function TopRightToolbar({ selected_contact }) {
    if (selected_contact === '' || selected_contact === null || selected_contact === undefined) {
        return (
            <div className="toolbar">
                <p id="selected-contact"> No contact selected </p>
            </div>
        );
    }

    return (
        <div className="toolbar">
            <div className="avatar">
                <img alt="" src={selected_contact.user.profilePic} />
            </div>
            <p id="selected-contact"> { selected_contact.user.displayName } </p>
        </div>
    );
}

export default TopRightToolbar;