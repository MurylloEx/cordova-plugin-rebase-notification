package com.rebase.local.notifications;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;

import com.rebase.local.notifications.RebaseHelper;

public class RebaseReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

    RebaseHelper helper = new RebaseHelper(context);
    helper.showNotification(intent, 
      intent.getIntExtra("notificationId", 1), 
      intent.getStringExtra("notificationChannelId"), 
      intent.getStringExtra("notificationTitle"), 
      intent.getStringExtra("notificationText"), 
      intent.getStringExtra("notificationIconPath"));
  }

}
