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
import android.graphics.drawable.Icon;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

import com.rebase.local.notifications.RebaseHelper;

public class RebaseNotifications extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
      if (action.equals("showNotification")) {
        int notificationId = args.getInt(0);
        String channelId = args.getString(1);
        String title = args.getString(2);
        String text = args.getString(3);
        String iconPath = args.getString(4);

        RebaseHelper helper = new RebaseHelper(this.cordova.getActivity().getApplicationContext());
        helper.showNotification(this.cordova.getActivity(), 
          notificationId, channelId, title, text, iconPath, callbackContext);

        return true;
      } else 
      if (action.equals("scheduleNotification")){
        int notificationId = args.getInt(0);
        String channelId = args.getString(1);
        String title = args.getString(2);
        String text = args.getString(3);
        String iconPath = args.getString(4);
        long time = args.getLong(5);

        RebaseHelper helper = new RebaseHelper(this.cordova.getActivity().getApplicationContext());
        helper.scheduleNotification(notificationId, channelId, title, text, iconPath, time, callbackContext);

        return true;
      }
      return false;
    }

}
