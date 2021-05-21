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
    //Define o contexto da aplicação
    this.m_Context = context;
  }

  public Context getContext(){
    //Retorna o contexto da aplicação
    return this.m_Context;
  }

  public void setContext(Context context){
    //Define o contexto da aplicação
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
        } catch (IOException ignored) {}
      }
    }
    return bitmap;
  }

  public void showNotification(
    Intent intent,
    int notificationId, 
    String channelId, 
    String title, 
    String text, 
    String iconPath) 
  {
    //Obtém a PendingIntent a partir do contexto da aplicação e a Intent passada como parâmetro
    PendingIntent pendingIntent = PendingIntent.getActivity(this.m_Context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    NotificationManager notificationManager = (NotificationManager)this.m_Context.getSystemService(Context.NOTIFICATION_SERVICE);
    
    //Configura um canal de notificações caso o sistema subjacente 
    //seja maior ou igual ao Android Oreo (>= Android 8.0)
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
      @SuppressLint("WrongConstant")
      NotificationChannel notificationChannel =
        new NotificationChannel(channelId, "Notifications", NotificationManager.IMPORTANCE_MAX);

      //Configura o canal de notificações
      notificationChannel.setDescription("Rebase Notifications");
      notificationChannel.enableLights(true);
      notificationChannel.enableVibration(true);
      notificationChannel.setVibrationPattern(new long[]{ 600, 400, 800 });
      notificationManager.createNotificationChannel(notificationChannel);
    }
    //Constrói a notificação e define seus atributos
    Builder notificationBuilder = 
      new Builder(this.m_Context, channelId)
        .setSmallIcon(Icon.createWithBitmap(this.getBitmapFromAsset(this.m_Context.getAssets(), iconPath)))
        .setContentTitle(title)
        .setContentText(text)
        .setAutoCancel(false)
        .setContentIntent(pendingIntent)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        .setPriority(Notification.PRIORITY_MAX);
    //Aciona a notificação no celular
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
    //Obtém a Intent a partir do contexto e da Activity
    Intent intent = new Intent(this.m_Context, activity.getClass());
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    //Invoca a função de exibir notificação utilizando a Intent obtida.
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
    //Define a Intent da notificação com base no contexto e na classe Receiver do BroadcastReceiver
    Intent intent = new Intent(this.m_Context, RebaseReceiver.class)
      .putExtra("notificationId", notificationId)
      .putExtra("notificationChannelId", channelId)
      .putExtra("notificationTitle", title)
      .putExtra("notificationText", text)
      .putExtra("notificationIconPath", iconPath);
    //Cria uma PendingIntent para a Intent da notificação
    PendingIntent pendingIntent = PendingIntent.getBroadcast(this.m_Context, 
      notificationId + 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    //Obtém a referência ao gerenciador de alarmes para agendar a notificação
    AlarmManager manager = (AlarmManager)this.m_Context.getSystemService(Context.ALARM_SERVICE);
    manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    //Retorna o código de operação bem-sucedida ao Cordova
    callbackContext.success();
  }

}
