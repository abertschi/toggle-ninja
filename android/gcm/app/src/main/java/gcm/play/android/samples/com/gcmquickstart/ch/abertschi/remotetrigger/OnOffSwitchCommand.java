package gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger;

import android.content.Context;

/**
 * Created by abertschi on 07/07/16.
 */
public abstract class OnOffSwitchCommand extends AbstractCommand {

    public OnOffSwitchCommand(String command) {
        super(command);
    }

    @Override
    public void init(Context context) {}

    @Override
    public CommandResponse apply(Context context, String command, String arg, String payload) {
        String notification = "wrong input";
        if (arg != null) {
            arg = arg.toUpperCase();
            if (arg != null) {
                boolean enabled = false;
                arg = arg.toUpperCase();
                if (arg.equals("ON")) {
                    enabled = true;
                    enable(context, true);
                } else if (arg.equals("OFF")) {
                    enabled = false;
                    enable(context, false);
                } else if (arg.equals("SWITCH")) {
                    enabled = !isEnabled(context);
                    enable(context, enabled);
                }
                notification = String.format("%s was %s", command, enabled ? "enabled" : "disabled");
            }

        }
        return new CommandResponse().setShowNotification(true).setNotificationAutoCancel(true).setNotificationMessage(notification);
    }

    protected abstract void enable(Context context, boolean enable);

    protected abstract boolean isEnabled(Context context);
}
