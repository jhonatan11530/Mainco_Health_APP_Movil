package com.example.mainco;

public class OPS {
    private String tarea;

    public OPS(){

    }
    public OPS(String tarea){
        this.tarea = tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getTarea() {
        return tarea;
    }
    public String toString(){
        return  tarea;
    }
}
