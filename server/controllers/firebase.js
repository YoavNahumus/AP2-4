
const FirebaseTokens = {};


const request = require('request');

sendPushNotification = (fcm_token, title, body, notificationType,moredat) => {
    var message = { 
        to: fcm_token, 
    
        
        notification: {
            title: title, 
            body: body 
        },
        
        data: {  //you can send only notification or only data(or include both)
            my_key: {
                type: notificationType,
                dat : moredat
            },
        }
    }
    
    const endpoint = 'https://fcm.googleapis.com/fcm/send';
    const headers = {
      'Authorization': 'key=AAAAfmeqgBQ:APA91bH0mCCrprq2k5s-EKm-8PwdJS38FPJVtQe9FPgf_MdZs8H3CYK7M52iVRg1VxNcBUvavZCso7fYhNQZzp13Hu969WaI97hTmJuWsf1QPKHzPOG-OLhxSe6tQrkXSnCVOd3j30pu',
      'Content-Type': 'application/json'
    };
    
    // Send the message via the FCM API
    request.post({
      url: endpoint,
      headers: headers,
      body: JSON.stringify(message)
    }, function(error, response, body) {
      if (error) {
        console.error('Error sending message:', error);
      } else if (response.statusCode !== 200) {
        console.error('Error sending message, status code:', response.statusCode);
      } else {
        console.log('Successfully sent message:', body);
      }
    });

}


const updateFireToken = async (req, res) => {
    if (req.body.firetoken == null || req.body.firetoken == "") {
        res.status(400);
    }
    const user = req.decoded.username;
    FirebaseTokens[user] = req.body.firetoken;
    res.status(200);
}

const sendMessage = (to, from, content,id) => {
    console.log("sending msg firebase", to, from);
    console.log(FirebaseTokens);
    if (FirebaseTokens[to]) {
        sendPushNotification(FirebaseTokens[to], "Chatapp",from + " : " + content, "newMessage", id);
    }
}

const updateChats = (user, user2) => {
    if (FirebaseTokens[user]) {
        if (user2) {
            console.log(user2);
            sendPushNotification(FirebaseTokens[user], "Chatapp", user2 + " Opened A New Chat With You!", "newChat","bla");
        } else {
            sendPushNotification(FirebaseTokens[user], "Chatapp", "Your chat list got updated!", "newChat", "bla");
        }
    }
}


module.exports = {
    sendMessage,
    updateChats,
    updateFireToken
};