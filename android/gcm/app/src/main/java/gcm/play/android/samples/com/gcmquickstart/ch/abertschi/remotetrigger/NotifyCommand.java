package gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import org.json.JSONException;
import org.json.JSONObject;

import gcm.play.android.samples.com.gcmquickstart.MainActivity;
import gcm.play.android.samples.com.gcmquickstart.R;

/**
 * Created by abertschi on 12/07/16.
 */
public class NotifyCommand extends AbstractCommand {

    public NotifyCommand() {
        super("notify");
    }

    @Override
    public void init(Context context) {
    }

    @Override
    public CommandResponse apply(Context context, String command, String arg, String payload) {
        String msg = null;
        String url = null;
        String title = null;
        try {
            JSONObject reader = new JSONObject(payload);
            title = reader.optString("title");
            msg = reader.optString("msg");
            url = reader.optString("url");
        } catch (JSONException e) {
            msg = payload;
        }
        if (title != null && title.isEmpty()) {
            title = null;
        }

        boolean openBrowser = url != null && !url.isEmpty() ? true: false;
        return new CommandResponse().setNotificationMessage(msg).setNotificationAutoCancel(false).setShowNotification(true)
                .setOpenBrowser(openBrowser).setBrowserUrl(url).setNotificationTitle(title);
    }
}
