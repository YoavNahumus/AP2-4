const express = require('express');
const { createToken } = require('../controllers/tokens');
const router = express.Router();

router.post('/Tokens', createToken);

module.exports = router;
