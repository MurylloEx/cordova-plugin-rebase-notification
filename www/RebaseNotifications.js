const exec = require('cordova/exec');

module.exports.showNotification = function (notificationId, channelId, title, text, success, error) {
  exec(success, error, 'RebaseNotifications', 'showNotification', [
    notificationId, channelId, title, text
  ]);
};
