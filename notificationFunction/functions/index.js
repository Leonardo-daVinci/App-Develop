'use strict'

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.sendNotification = functions.database.ref('/notifications/{user_id}/{notification_id}')
                                .onWrite((change,context) => {

    const user_id = context.params.user_id;
    const notification_id = context.params.notification_id;

    console.log('User ID = ', user_id);
   // console.log('Notification ID = ', notification_id);

   if( !change.after.val()){
        return console.log('Notification has been deleted: ',notification_id);
   }

   const device_token = admin.database().ref(`/Users/${user_id}/token`).once('value');
   return device_token.then(result =>{

    const tokenID = result.val();
    
   const payload = {
    notification: {
        title : "Friend Request",
        body: "You have a new friend request",
        icon: "default"
    }
   };

   return admin.messaging().sendToDevice(tokenID, payload);
   
    });

});