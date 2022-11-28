var express = require('express');
var router = express.Router();
var { getMessaging } = require('firebase-admin/messaging');

/* GET home page. */
router.post('/send_message', function(req, res, next) {

  console.log(
    "Received notification for ",
    req.body
  )
  
  const { token, title, message } = req.body

  const notificationMessage = {
    notification: {
      title: title,
      body: message
    },
    token: token
  };

  // Send a message to the device corresponding to the provided
  // registration token.
  getMessaging().send(notificationMessage)
    .then((response) => {
      // Response is a message ID string.
      console.log('Successfully sent message:', response);
      res.send("Success")
    })
    .catch((error) => {
      console.log('Error sending message:', error);
      res.send("Error")
    });
});

module.exports = router;
