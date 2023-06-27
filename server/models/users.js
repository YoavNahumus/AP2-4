const client = require('./client.js');

exports.getUsers = () => {
    return client.db('chat').collection('users').find({}).toArray();
};

exports.getUser = (username) => {
    return client.db('chat').collection('users').findOne({ username: username });
};

exports.createUser = (user) => {
    return client.db('chat').collection('users').insertOne({ username: user.username, password: user.password, profilePic: user.profilePic, displayName: user.displayName });
};