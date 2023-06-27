const jwt = require('jsonwebtoken');

const key = 'secret';

const authenticate = (req, res, next) => {
    if (!req.headers.authorization) {
        res.status(401).json({ message: 'Unauthorized' });
        return;
    }
    const token = req.headers.authorization.split(' ')[1];
    try {
        const data = jwt.verify(token, key);
        req.decoded = data;
        next();
    } catch (err) {
        res.status(401).json({ message: 'Unauthorized' });
    }
};

const generateToken = (user) => {
    const payload = {
        username: user.username
    };
    return jwt.sign(payload, key);
};

const verifyToken = (token) => {
    try {
        const data = jwt.verify(token, key);
        return data;
    } catch (err) {
        return null;
    }
};

module.exports = {
    authenticate,
    generateToken,
    verifyToken
};