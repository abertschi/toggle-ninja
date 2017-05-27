package gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger;

import android.content.Context;

/**
 * Created by abertschi on 07/07/16.
 */
public interface Command {

    void init(Context context);

    boolean canApply(String command);

    CommandResponse apply(Context context, String command, String arg, String payload);
}
