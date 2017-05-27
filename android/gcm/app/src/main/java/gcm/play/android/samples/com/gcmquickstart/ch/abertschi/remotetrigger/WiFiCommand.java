package gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by abertschi on 07/07/16.
 */
public class WiFiCommand extends OnOffSwitchCommand {

    public WiFiCommand() {
        super("WIFI");
    }

    @Override
    protected void enable(Context context, boolean enable) {
        turnOnOffWifi(context, enable);
    }

    @Override
    protected boolean isEnabled(Context context) {
        return isWifiEnabled(context);
    }

    public static void turnOnOffWifi(Context context, boolean isTurnToOn) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(isTurnToOn);
    }

    public static boolean isWifiEnabled(Context context)
    {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }
}
