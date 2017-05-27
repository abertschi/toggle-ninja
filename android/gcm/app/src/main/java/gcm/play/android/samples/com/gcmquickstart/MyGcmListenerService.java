/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gcm.play.android.samples.com.gcmquickstart;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger.BluetoothCommand;
import gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger.Command;
import gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger.CommandResponse;
import gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger.HotspotCommand;
import gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger.NotifyCommand;
import gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger.TextToSpeechCommand;
import gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger.WiFiCommand;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    private List<Command> commands;

    private Random random = new Random();

    public MyGcmListenerService() {
        commands = new ArrayList<>();
        commands.add(new BluetoothCommand());
        commands.add(new HotspotCommand());
        commands.add(new WiFiCommand());
        commands.add(new NotifyCommand());
        commands.add(new TextToSpeechCommand());
    }

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String command = data.getString("command");
        String arg = data.getString("arg");
        String payload = data.getString("payload");
        String message = String.format("Setting %s to %s", command, arg);

        for (Command c : commands) {
            if (c.canApply(command)) {
                Log.d(TAG, "Executing command: " + command + c.getClass());
                CommandResponse r = c.apply(this, command, arg, payload);
                sendNotification(r);
                break;
            }
        }

        Log.d(TAG, "Message: " + message);
    }


    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(CommandResponse message) {
        Intent intent;
        int m = random.nextInt(9999 - 1000) + 1000;
        int id = message.isNotificationAutoCancel() ? 0 : m;

        if (message.isOpenBrowser()) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(message.getBrowserUrl()));
        } else {
            intent = new Intent(this, MainActivity.class);
        }


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle(message.getNotificationTitle() != null ? message.getNotificationTitle() : "Toggle Nina")
                .setContentText(message.getNotificationMessage())
                .setAutoCancel(message.isNotificationAutoCancel())
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setGroup("toggleNinja");

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id /* ID of notification */, notificationBuilder.build());
    }
}
