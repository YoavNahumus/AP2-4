const User = require('../models/users');
const authenticator = require('../authenticator');

const getUser = async (req, res) => {
    const id = req.url.split('/').pop();
    if (req.decoded.username !== id) {
        res.status(401).json({ message: 'Unauthorized' });
        return;
    }
    const user = await User.getUser(id);
    res.status(200).json({ username: user.username, displayName: user.displayName, profilePic: user.profilePic });
};

const createUser = async (req, res) => {
    const user = req.body;
    if (!user.username || !user.password || !user.profilePic || !user.displayName) {
        res.status(400).json({ message: 'Invalid request' });
        return;
    }
    if ((await User.getUser(user.username)) !== null) {
        res.status(409).json({ message: 'User already exists' });
        return;
    }
    await User.createUser(user);
    const token = authenticator.generateToken(user);
    res.status(200).json(token);
};

module.exports = {
    getUser,
    createUser
};
