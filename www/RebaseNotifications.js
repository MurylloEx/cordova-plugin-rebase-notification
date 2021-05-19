const exec = require('cordova/exec');

module.exports.showNotification = function (notificationId, channelId, title, text, iconPath, success, error) {
  return exec(
    success, 
    error, 
    'RebaseNotifications', 
    'showNotification', 
    [ notificationId, channelId, title, text, iconPath ]
  );
};

module.exports.scheduleNotification = function (notificationId, channelId, title, text, iconPath, time, success, error) {
  return exec(
    success, 
    error, 
    'RebaseNotifications', 
    'scheduleNotification', 
    [ notificationId, channelId, title, text, iconPath, time ]
  );
};