package gcm.play.android.samples.com.gcmquickstart.ch.abertschi.remotetrigger;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Created by abertschi on 14/07/16.
 */
public class TextToSpeechCommand extends AbstractCommand implements TextToSpeech.OnInitListener {

    private TextToSpeech speech;
    private String speak;

    public TextToSpeechCommand() {
        super("speak");
    }

    @Override
    public void init(Context context) {

    }

    @Override
    public CommandResponse apply(Context context, String command, String arg, final String payload) {
        this.speech = new TextToSpeech(context, this);
        return new CommandResponse();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            speech.setLanguage(Locale.UK);
            speech.speak("hi", TextToSpeech.QUEUE_FLUSH, null);

        }
    }
}
