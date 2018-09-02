'use strict'

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.sendNotification = functions.database.ref('/notifications/{user_id}/{notification_id}').onWrite((change,context) => {

    const user_id = context.params.user_id;

    console.log('User ID = ', user_id);
   // console.log('Notification ID = ', notification_id);


});