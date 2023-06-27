const express = require('express');
const bodyParser = require('body-parser');

const cor = require('cors');

require('./socket').setUpSocket();

const app = express();

app.use(cor());

app.use(bodyParser.json({ limit: '10mb' }));
app.use(bodyParser.urlencoded({ extended: true, limit: '10mb' }));

app.use('/api/', require('./routes/users'));
app.use('/api/', require('./routes/tokens'));
app.use('/api/', require('./routes/chats'));
app.use('/api/', require('./routes/firebase'));


app.get('/', (req, res) => {
    res.redirect('http://localhost:3000');
});

app.listen(5000);
