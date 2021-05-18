package com.rebase.local.notifications;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.NotificationChannel;
import android.app.AlarmManager;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;

import java.io.File;

public class RebaseNotifications extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
      if (action.equals("showNotification")) {
        int notificationId = args.getInt(0);
        String channelId = args.getString(1);
        String title = args.getString(2);
        String text = args.getString(3);
        //String iconPath = args.getString(4);
        this.showNotification(notificationId, channelId, title, text, callbackContext);
        return true;
      }
      return false;
    }

    private Uri getDefaultSound(){
      return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    private void showNotification(
      int notificationId, 
      String channelId, 
      String title, 
      String text, 
      CallbackContext callbackContext) 
    {
      Activity activity = this.cordova.getActivity();
      Context context = activity.getApplicationContext();

      Intent intent = new Intent(context, activity.getClass());
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

      PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
      NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        @SuppressLint("WrongConstant")
        NotificationChannel notificationChannel =
          new NotificationChannel(channelId, "Notification", NotificationManager.IMPORTANCE_MAX);

        //Configure Notification Channel
        notificationChannel.setDescription("Rebase Notifications");
        notificationChannel.enableLights(true);
        notificationChannel.setVibrationPattern(new long[]{200});
        notificationChannel.enableVibration(false);
        notificationManager.createNotificationChannel(notificationChannel);
      }

      Builder notificationBuilder = 
        new Builder(context, channelId)
          .setContentTitle(title)
          .setContentText(text)
          .setAutoCancel(false)
          .setSound(getDefaultSound()) //Utiliza o som padrão das notificações
          .setContentIntent(pendingIntent)
          .setPriority(Notification.PRIORITY_MAX);

      //if (iconPath != null){
      //    File mSaveBit = new File(iconPath);
      //    String filePath = mSaveBit.getPath();
      //    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
      //    notificationBuilder.setLargeIcon(bitmap);
      //}
      
      notificationManager.notify(notificationId, notificationBuilder.build());

      callbackContext.success("true");
    }

}
