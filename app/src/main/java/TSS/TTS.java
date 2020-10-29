package TSS;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TTS{
    private TextToSpeech textToSpeech;
    private boolean cargado = false;

    public void init(Context context) {
        final TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == android.speech.tts.TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.getDefault());
                    cargado = true;
                    if (result == android.speech.tts.TextToSpeech.LANG_MISSING_DATA || result == android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("erro", "ESTE LENGUAJE NO ES PERMITIDO");
                    } else {
                        textToSpeech.setPitch(1f);
                        textToSpeech.setSpeechRate(1f);

                    }
                }
            }

        };

        textToSpeech = new TextToSpeech(context, onInitListener);


    }


    public void onPause() {
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }


    public void speak(String text) {

        if (cargado) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}

