import "./main.css"
import "./forms.css"
import { Link, useNavigate } from "react-router-dom";
import React, { useState } from "react";
import $ from "jquery";

function Register() {

	const [user, setUser] = useState("");
	const [pass, setPass] = useState("");
	const [confirmPass, setConfirmPass] = useState("");
	const [displayName, setDisplayName] = useState("");

	const navigate = useNavigate();

	const getDataFromFile = (blob) => {
		return new Promise((resolve, reject) => {
			let reader = new FileReader();
			reader.onload = () => {
				resolve(reader.result);
			};
			reader.onerror = reject;
			reader.readAsDataURL(blob);
		})
	}

	const handleSubmit = (e) => {
		e.preventDefault();
		if (displayName.length < 2) {
			alert("displayName must be at least 2 cheracters long!");
			return;
		}
		if (user.length < 2) {
			alert("Username must be at least 2 cheracters long!");
			return;
		}
		if (confirmPass !== pass) {
			alert("Diffrent Confirmed Password And Password!\nHas To Be Identical!");
			return;
		}
		var paswd=  /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{7,15}$/;
		if (!String(pass).match(paswd)) {
			alert("Password is in an incorrect format, make sure its between 7-15 characters and has at least one numeric number and one special character");
			return;
		}

        const pictureInput = $("#picture").get(0);

        if (pictureInput.files.length === 0) {
            alert("Please select a picture!");
            return;
        }

        const file = pictureInput.files[0];

        if (file.type.split("/")[0] !== "image") {
            alert("File must be an image!");
            return;
		}

		async function request_func() {
			const filedata = await getDataFromFile(file)
			const data = {
				"username": user,
				"password": pass,
				"displayName": displayName,
				"profilePic": filedata
			}

			const res = await fetch('http://localhost:5000/api/Users', {
				'method': 'post',
				'headers': {
					'Content-Type': 'application/json'
				},
				'body': JSON.stringify(data)
			});
			if (res.status === 200) {
				navigate("/");
			} else if (res.status === 409) {
				alert("username allready taken! please choose another user");
			} else {
				alert("error login!");
			}
		}
		request_func()
	}

	return (
		<div className="Register">
			<div>
				<div className="green-top-bar" />
				<div className="background" />
				<div className="middle-div">
					<form onSubmit={handleSubmit}>
						<div className="mb-3 row">
							<label htmlFor="inputUsername" className="col-sm-3 col-form-label">Username</label>
							<div className="col-sm-9">
								<input type="text" value={user} onChange={(e) => setUser(e.target.value)} className="form-control form-input" id="inputUsername" />
							</div>
						</div>
						<div className="mb-3 row">
							<label htmlFor="inputPassword" className="col-sm-3 col-form-label">Password</label>
							<div className="col-sm-9">
								<input type="password" value={pass} onChange={(e) => setPass(e.target.value)} className="form-control form-input" id="inputPassword" />
							</div>
						</div>
						<div className="mb-3 row">
							<label htmlFor="inputPasswordConfirm" className="col-sm-3 col-form-label">Confirm password</label>
							<div className="col-sm-9">
								<input type="password" value={confirmPass} onChange={(e) => setConfirmPass(e.target.value)}className="form-control form-input" id="inputPasswordConfirm" />
							</div>
						</div>
						<div className="mb-3 row">
							<label htmlFor="displayName" className="col-sm-3 col-form-label">Display name</label>
							<div className="col-sm-9">
								<input type="text" value={displayName} onChange={(e) => setDisplayName(e.target.value)} className="form-control form-input" id="displayName" />
							</div>
						</div>
						<div className="mb-3 row">
							<label htmlFor="picture" className="col-sm-3 col-form-label">Picture</label>
							<div className="col-sm-9">
								<input type="file" accept="image/*" className="form-control form-file-input" id="picture" />
							</div>
						</div>
						<br /><br />
						<div className="mb-3 row text-center">
							<div className="col-sm-3 d-grid">
								<button type="submit" className="btn btn-primary">Register</button>
							</div>
							<div className="col-sm-2" />
							<div className="col-sm-5">Already registered? <Link to="/">Click Here</Link> to login</div>
							<div className="col-sm-2" />
						</div>
					</form>
				</div>
			</div>
		</div>
	);
}

export default Register;