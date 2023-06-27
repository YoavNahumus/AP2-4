const Chat = require('../models/chats');
const User = require('../models/users');
const Socket = require('../socket');
const FireModule =  require('../controllers/firebase')

const getChats = async (req, res) => {
    const user = req.decoded.username;
    const chats = await Chat.getChats(user);
    res.status(200).json(chats);
};

const createChat = async (req, res) => {
    const user1 = req.decoded.username;
    const reqUser = req.body.username;
    const user2 = await User.getUser(reqUser);
    if (user2 === null) {
        res.status(404).json({ message: 'User not found' });
        return;
    }

    if ((await Chat.getChatFromUsers(user1, reqUser) !== null)) {
        res.status(400).json({ message: 'Chat already exists' });
        return;
    }

    const chat = await Chat.createChat(user1, reqUser);
    Socket.updateChats(reqUser);
    FireModule.updateChats(reqUser,user1);
    res.status(200).json({ id: chat.insertedId, user: { username: user2.username, displayName: user2.displayName, profilePic: user2.profilePic } });
};

const getChat = async (req, res) => {
    const user = req.decoded.username;
    const chatId = req.url.split('/').pop();
    if (!(await Chat.isValidChatId(chatId))) {
        res.status(400).json({ message: 'Invalid chat id' });
        return;
    }
    const chat = await Chat.getChat(chatId);
    if (!(await Chat.userInChat(user, chatId))) {
        res.status(401).json({ message: 'Unauthorized' });
        return;
    }

    res.status(200).json(chat);
};

const deleteChat = async (req, res) => {
    const user = req.decoded.username;
    const chatId = req.url.split('/').pop();
    if (!(await Chat.userInChat(user, chatId))) {
        res.status(400).json({ message: 'Invalid chat id' });
        return;
    }
    if (!(await Chat.userInChat(user, chatId))) {
        res.status(401).json({ message: 'Unauthorized' });
        return;
    }

    await Chat.deleteChat(chatId);
    const users = await Chat.getUsers(chatId);
    users.filter((u) => u.username !== user).forEach((u) => {
        Socket.updateChats(u.username);
        FireModule.updateChats(u.username, null);
    });
    res.status(200);
};

const createMessage = async (req, res) => {
    const user = req.decoded.username;
    const split = req.url.split('/');
    const chatId = split[split.length - 2];
    if (!(await Chat.userInChat(user, chatId))) {
        res.status(400).json({ message: 'Invalid chat id' });
        return;
    }
    const message = req.body.msg;
    if (message === '') {
        res.status(400).json({ message: 'Message cannot be empty' });
        return;
    }
    if (!(await Chat.userInChat(user, chatId))) {
        res.status(401).json({ message: 'Unauthorized' });
        return;
    }
    const msg = await Chat.createMessage(user, chatId, message);
    const users = await Chat.getUsers(chatId);
    console.log(users);
    users.filter((u) => u !== user).forEach((u) => Socket.sendMessage(u, user));
    users.filter((u) => u !== user).forEach((u) => FireModule.sendMessage(u, user, message));
    res.status(200).json(msg);
};

const getMessages = async (req, res) => {
    const user = req.decoded.username;
    const split = req.url.split('/');
    const chatId = split[split.length - 2];
    if (!(await Chat.userInChat(user, chatId))) {
        res.status(400).json({ message: 'Invalid chat id' });
        return;
    }
    if (!(await Chat.userInChat(user, chatId))) {
        res.status(401).json({ message: 'Unauthorized' });
        return;
    }
    const messages = await Chat.getMessages(chatId);
    res.status(200).json(messages);
};

module.exports = {
    createChat,
    getChats,
    getChat,
    deleteChat,
    createMessage,
    getMessages
};
