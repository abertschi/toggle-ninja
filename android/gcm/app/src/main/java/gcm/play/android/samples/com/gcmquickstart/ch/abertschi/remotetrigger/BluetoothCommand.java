package gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by abertschi on 07/07/16.
 */
public class BluetoothCommand extends OnOffSwitchCommand {

    public BluetoothCommand() {
        super("BLUETOOTH");
    }

    @Override
    protected void enable(Context context, boolean enable) {
        turnOnOffBluetooth(enable);
    }

    @Override
    protected boolean isEnabled(Context context) {
        return isBluetoothEnabled();
    }

    public static void turnOnOffBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            bluetoothAdapter.enable();
        }
        else if(!enable && isEnabled) {
            bluetoothAdapter.disable();
        }
    }

    public static boolean isBluetoothEnabled()
    {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.isEnabled();
    }
}
