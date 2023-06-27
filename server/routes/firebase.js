const express = require('express');
const router = express.Router();
const authenticate = require('../authenticator').authenticate;
const { updateFireToken } = require("../controllers/firebase");

router.post('/Firebase', authenticate, updateFireToken);

module.exports = router;
