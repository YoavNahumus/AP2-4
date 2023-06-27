const express = require('express');
const { createChat, getChats, getChat, deleteChat, createMessage, getMessages } = require('../controllers/chats');
const router = express.Router();
const authenticate = require('../authenticator').authenticate;

router.post('/Chats', authenticate, createChat);
router.get('/Chats', authenticate, getChats);

router.post('/Chats/*/Messages', authenticate, createMessage);
router.get('/Chats/*/Messages', authenticate, getMessages);

router.get('/Chats/*', authenticate, getChat);
router.delete('/Chats/*', authenticate, deleteChat);

module.exports = router;
