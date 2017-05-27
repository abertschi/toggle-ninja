package gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger;

import android.content.Context;

/**
 * Created by abertschi on 07/07/16.
 */
public abstract class AbstractCommand implements Command {

    protected final String command;

    public AbstractCommand(String command)
    {
        this.command = command;
    }

    @Override
    public boolean canApply(String c) {
        return command != null && command.toUpperCase().equals(c.toUpperCase());
    }
}
