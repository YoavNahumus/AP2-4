import "./main.css"
import "./forms.css"
import { Link, useNavigate} from "react-router-dom";
import { useState } from "react";
import CurrentUser from "./Users/CurrentUser";
import token from "./Users/CurrentToken";


function Login() {
	const [user, setUser] = useState("");
	const [pass, setPass] = useState("");
	const navigate = useNavigate();


	async function handleLogin() {
		const data = {
			username: user,
			password: pass
		}
		const res = await fetch('http://localhost:5000/api/Tokens', {
			'method': 'post',
			'headers': {
				'Content-Type' : 'application/json'
			},
			'body': JSON.stringify(data)
		})
		if (res.status === 404) {
			return false;
		}
		token.key = await res.json();
		return true;
	}

	async function handleSubmit(e) {
		e.preventDefault();
		const value = await handleLogin();
		if (value) {
			CurrentUser.user = user;
			navigate("/Chat")
		} else {
			//failed login
			alert("Incurrect User or Password!");
		}
	}

    return (
    <div className="Login">
        <div className="green-top-bar"></div>
		<div>
			<div className="background" />
			<form className="middle-div" onSubmit={handleSubmit}>
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
				<br /><br />
				<div className="mb-3 row text-center">
					<div className="col-sm-3 d-grid">
						<button type="submit" className="btn btn-primary">Login</button>
					</div>
					<div className="col-sm-2" />
					<div className="col-sm-5">Not registered? <Link to="/Register">Click Here</Link> to register</div>
					<div className="col-sm-2" />
				</div>
			</form>
		</div>
    </div>
    );
}

export default Login;