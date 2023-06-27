const User = require('../models/users');
const authenticator = require('../authenticator');

const createToken = async (req, res) => {
    const data = req.body;
    if (!data.username || !data.password) {
        res.status(400).json({ message: 'Invalid request' });
        return;
    }
    const user = await User.getUser(data.username);
    if (!user || user.password !== data.password) {
        res.status(404).json({ message: 'User not found' });
        return;
    }
    const token = authenticator.generateToken(data);
    res.status(200).json(token);
};

module.exports = {
    createToken
};
