package com.example.App;

@SuppressWarnings("ALL")
public class motivoparo {
    private String paro;

    public motivoparo (){

    }
    public motivoparo(String paro){
        this.paro = paro;
    }

    public String getParo() {
        return paro;
    }

    public void setParo(String paro) {
        this.paro = paro;
    }

    public String toString(){
        return  paro;
    }
}
