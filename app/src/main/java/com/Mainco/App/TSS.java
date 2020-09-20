package com.Mainco.App;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TSS {
    private TextToSpeech textToSpeech;
    private  boolean cargado=false;
    public void init(Context context){
      final  TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == android.speech.tts.TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.getDefault());
                    cargado =true;
                    if (result == android.speech.tts.TextToSpeech.LANG_MISSING_DATA || result == android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("erro","ESTE LENGUAJE NO ES PERMITIDO");
                    } else {

                        textToSpeech.setPitch(1.0f);
                        textToSpeech.setSpeechRate(1.0f);

                    }
                }
            }
        };

        textToSpeech = new TextToSpeech(context,onInitListener);

    }


    public void onStop() {
        if (textToSpeech != null) {
            System.out.println("ESTO SE DETUBO "+textToSpeech.stop());
            textToSpeech.stop();
        }
    }

    public void speak(String text){
        if(cargado){
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
        }
    }
}
