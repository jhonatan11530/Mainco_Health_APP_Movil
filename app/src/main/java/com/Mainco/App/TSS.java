package com.Mainco.App;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Locale;

public class TSS{

    private TextToSpeech textToSpeech;
    private  boolean cargado=false;

    public void init(Context context){
        textToSpeech = new TextToSpeech(context,onInitListener);
    }
    private TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
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

    public void shutDown(){
        textToSpeech.shutdown();
    }

    public void speak(String text){
        if(cargado){
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

}
