package com.rebase.local.notifications;

import org.apache.cordova.CallbackContext;

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
import android.graphics.drawable.Icon;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class RebaseHelper {
  
  private Context m_Context = null;

  RebaseHelper(Context context){
    this.m_Context = context;
  }

  public Context getContext(){
    return this.m_Context;
  }

  public void setContext(Context context){
    this.m_Context = context;
  }

  public Bitmap getBitmapFromAsset(AssetManager mgr, String path) {
    InputStream inputStrm = null;
    Bitmap bitmap = null;
    try {
        inputStrm = mgr.open(path);
        bitmap = BitmapFactory.decodeStream(inputStrm);
    } catch (final IOException e) {
        bitmap = null;
    } finally {
        if (inputStrm != null) {
            try {
                inputStrm.close();
            } catch (IOException ignored) {
            }
        }
    }
    return bitmap;
  }

  public Uri getDefaultSound(){
    return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
  }

  public void showNotification(
    Intent intent,
    int notificationId, 
    String channelId, 
    String title, 
    String text, 
    String iconPath) 
  {
    PendingIntent pendingIntent = PendingIntent.getActivity(this.m_Context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    NotificationManager notificationManager = (NotificationManager)this.m_Context.getSystemService(Context.NOTIFICATION_SERVICE);

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
      @SuppressLint("WrongConstant")
      NotificationChannel notificationChannel =
        new NotificationChannel(channelId, "Notifications", NotificationManager.IMPORTANCE_MAX);

      //Configure Notification Channel
      notificationChannel.setDescription("Rebase Notifications");
      notificationChannel.enableLights(true);
      notificationChannel.setVibrationPattern(new long[]{200});
      notificationChannel.enableVibration(false);
      notificationManager.createNotificationChannel(notificationChannel);
    }

    Builder notificationBuilder = 
      new Builder(this.m_Context, channelId)
        .setSmallIcon(Icon.createWithBitmap(this.getBitmapFromAsset(this.m_Context.getAssets(), iconPath)))
        .setContentTitle(title)
        .setContentText(text)
        .setAutoCancel(false)
        .setSound(getDefaultSound()) //Utiliza o som padrão das notificações
        .setContentIntent(pendingIntent)
        .setPriority(Notification.PRIORITY_MAX);
    
    notificationManager.notify(notificationId, notificationBuilder.build());
  }

  public void showNotification(
    Activity activity,
    int notificationId, 
    String channelId, 
    String title, 
    String text, 
    String iconPath,
    CallbackContext callbackContext) 
  {
    Intent intent = new Intent(this.m_Context, activity.getClass());
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

    this.showNotification(intent, notificationId, channelId, title, text, iconPath);
    callbackContext.success();
  }

  public void scheduleNotification(
    int notificationId, 
    String channelId, 
    String title, 
    String text, 
    String iconPath,
    long time, 
    CallbackContext callbackContext)
  {
    Intent intent = new Intent(this.m_Context, RebaseReceiver.class)
      .putExtra("notificationId", notificationId)
      .putExtra("notificationChannelId", channelId)
      .putExtra("notificationTitle", title)
      .putExtra("notificationText", text)
      .putExtra("notificationIconPath", iconPath);

    PendingIntent pendingIntent = PendingIntent.getBroadcast(this.m_Context, 
      notificationId + 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    AlarmManager manager = (AlarmManager)this.m_Context.getSystemService(Context.ALARM_SERVICE);
    manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);

    callbackContext.success();
  }

}
