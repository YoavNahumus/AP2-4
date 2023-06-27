const client = require('./client.js');
const User = require('./users.js');
const ObjectId = require('mongodb').ObjectId;

exports.getChats = async (user) => {
    return await Promise.all((await client.db('chat').collection('chats').find({ users: { $all: [user] } }).toArray()).map(async (chat) => {
        const otherUser = await User.getUser(chat.users.filter(u => u !== user)[0]);
        const lastMessage = chat.messages[chat.messages.length - 1];
        if (lastMessage === undefined) return {
            id: chat._id,
            user: {
                username: otherUser.username,
                displayName: otherUser.displayName,
                profilePic: otherUser.profilePic
            },
            lastMessage: null
        };
        return {
                id: chat._id,
                user: {
                    username: otherUser.username,
                    displayName: otherUser.displayName,
                    profilePic: otherUser.profilePic
                },
                lastMessage: {
                    id: lastMessage.id,
                    created: lastMessage.created,
                    content: lastMessage.content
                }
        };
    }));
}

exports.getChat = async (chatId) => {
    const chat = await client.db('chat').collection('chats').findOne({ _id: new ObjectId(chatId) });
    const users = await Promise.all(chat.users.map(async (user) => {
        const u = await User.getUser(user);
        return {
            username: u.username,
            displayName: u.displayName,
            profilePic: u.profilePic
        };
    }));
    const messages = chat.messages.map((message) => {
        const sender = users.find((user) => user.username === message.sender.username);
        return {
            id: message.id,
            created: message.created,
            sender: {
                username: sender.username,
                displayName: sender.displayName,
                profilePic: sender.profilePic
            },
            content: message.content
        };
    });
    return { id: chat._id, users: users, messages: messages };
}

exports.createChat = (user1, user2) => {
    return client.db('chat').collection('chats').insertOne({ users: [user1, user2], messages: [] });
}

exports.deleteChat = (chatId) => {
    return client.db('chat').collection('chats').deleteOne({ _id: new ObjectId(chatId) });
}

exports.createMessage = async (user, chatId, message) => {
    const now = new Date();
    const sender = await User.getUser(user);
    client.db('chat').collection('chats').updateOne({ _id: new ObjectId(chatId) }, { $push: { messages: { id: 0, created: now, sender: {username: sender.username}, content: message} } });
    return { id: 0, created: now, sender: { username: sender.username, displayName: sender.displayName, profilePic: sender.profilePic }, content : message };
}

exports.getMessages = async (chatId) => {
    return (await client.db('chat').collection('chats').findOne({ _id: new ObjectId(chatId) })).messages;
}

exports.userInChat = async (user, chatId) => {
    const chat = await client.db('chat').collection('chats').findOne({ _id: new ObjectId(chatId) });
    if (!chat) return false;
    return chat.users.includes(user);
}

exports.isValidChatId = async (chatId) => {
    return ObjectId.isValid(chatId) && await client.db('chat').collection('chats').findOne({ _id: new ObjectId(chatId) }) !== null;
}

exports.getUsers = async (chatId) => {
    const chat = await client.db('chat').collection('chats').findOne({ _id: new ObjectId(chatId) });
    if (!chat) return [];
    return chat.users;
}

exports.getChatFromUsers = async (user1, user2) => {
    const chat = await client.db('chat').collection('chats').findOne({ users: { $all: [user1, user2] } });
    if (!chat) return null;
    return chat._id;
}
