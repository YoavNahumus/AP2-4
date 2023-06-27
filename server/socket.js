const { Server } = require('socket.io');
const authenticator = require('./authenticator');

const sockets = {};

exports.setUpSocket = () => {
    const io = new Server(5001, {
        cors: {
            origin: '*',
            methods: ['GET', 'POST']
        }
    });
    
    io.on('connection', (socket) => {
        socket.emit('hello');

        socket.on('token', async (token) => {
            const user = authenticator.verifyToken(token);
            if (user === null) {
                socket.emit('invalidToken');
                socket.disconnect();
            } else {
                sockets[user.username] = socket;
                socket.emit('validToken');
            }
        });

        socket.on('disconnect', () => {
            const user = Object.keys(sockets).find((user) => sockets[user] === socket);
            if (user) {
                delete sockets[user];
            }
        });
    });
}

exports.sendMessage = (to, from) => {
    console.log("sending msg", to, from);
    if (sockets[to]) {
        sockets[to].emit('message', from);
    }
}

exports.updateChats = (user) => {
    if (sockets[user]) {
        console.log("update!");
        sockets[user].emit('updateChats');
    }
}
