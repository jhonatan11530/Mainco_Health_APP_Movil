package com.Mainco.App;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TTS {
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

                        textToSpeech.setPitch(0.8f);
                        textToSpeech.setSpeechRate(1.1f);

                    }
                }
            }
        };

        textToSpeech = new TextToSpeech(context, onInitListener);

    }


// --Commented out by Inspection START (26/10/2020 11:22 AM):
//    public void onStop() {
//        if (textToSpeech != null) {
//            System.out.println("ESTO SE DETUBO " + textToSpeech.stop());
//            textToSpeech.stop();
//        }
//    }
// --Commented out by Inspection STOP (26/10/2020 11:22 AM)

    public void speak(String text) {

        if (cargado) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
