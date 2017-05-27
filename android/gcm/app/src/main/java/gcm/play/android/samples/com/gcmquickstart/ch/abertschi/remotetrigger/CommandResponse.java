package gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger;

/**
 * Created by abertschi on 12/07/16.
 */
public class CommandResponse {

    private boolean showNotification = true;
    private String notificationMessage;
    private String notificationTitle = "";
    private boolean notificationAutoCancel = true;

    private boolean openBrowser = false;
    private String browserUrl;

    private boolean hasRepsonsePayload = false;
    private String responsePayload;

    public boolean isHasRepsonsePayload() {
        return hasRepsonsePayload;
    }

    public CommandResponse setHasRepsonsePayload(boolean hasRepsonsePayload) {
        this.hasRepsonsePayload = hasRepsonsePayload;
        return this;
    }

    public boolean isShowNotification() {
        return showNotification;
    }

    public CommandResponse setShowNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public CommandResponse setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
        return this;
    }

    public boolean isNotificationAutoCancel() {
        return notificationAutoCancel;
    }

    public CommandResponse setNotificationAutoCancel(boolean notificationAutoCancel) {
        this.notificationAutoCancel = notificationAutoCancel;
        return this;
    }

    public String getResponsePayload() {
        return responsePayload;
    }

    public CommandResponse setResponsePayload(String responsePayload) {
        this.responsePayload = responsePayload;
        return this;
    }

    public String getBrowserUrl() {
        return browserUrl;
    }

    public CommandResponse setBrowserUrl(String browserUrl) {
        this.browserUrl = browserUrl;
        return this;
    }

    public boolean isOpenBrowser() {
        return openBrowser;
    }

    public CommandResponse setOpenBrowser(boolean openBrowser) {
        this.openBrowser = openBrowser;
        return this;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public CommandResponse setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
        return this;
    }
}
