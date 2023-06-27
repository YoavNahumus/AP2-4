const express = require('express');
const { getUser, createUser } = require('../controllers/users');
const router = express.Router();

router.post('/Users', createUser);
router.get('/Users/*', require('../authenticator').authenticate, getUser);

module.exports = router;
