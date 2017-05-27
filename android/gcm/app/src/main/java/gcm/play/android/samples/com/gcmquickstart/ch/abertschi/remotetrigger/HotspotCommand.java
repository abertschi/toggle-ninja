package gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by abertschi on 07/07/16.
 */
public class HotspotCommand extends OnOffSwitchCommand {

    public HotspotCommand() {
        super("HOTSPOT");
    }

    @Override
    protected void enable(Context context, boolean enable) {
        turnOnOffHotspot(context, enable);
    }

    @Override
    protected boolean isEnabled(Context context) {
        return isHotspotEnabled(context);
    }

    public static void turnOnOffHotspot(Context context, boolean isOn) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiApControl apControl = WifiApControl.getApControl(wifiManager);
        if (apControl != null) {
            if (WiFiCommand.isWifiEnabled(context)) {
                WiFiCommand.turnOnOffWifi(context, false);
            }

            apControl.setWifiApEnabled(apControl.getWifiApConfiguration(), isOn);
        }
    }

    public static boolean isHotspotEnabled(Context context)
    {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiApControl apControl = WifiApControl.getApControl(wifiManager);
        return apControl.isWifiApEnabled();
    }
}
