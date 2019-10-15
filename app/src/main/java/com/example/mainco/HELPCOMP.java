package com.example.mainco;

public class HELPCOMP {
    private String nombres;

    public HELPCOMP(){

    }
    public HELPCOMP(String nombres){
        this.nombres = nombres;
    }

    public String getNombre() {
        return nombres;
    }

    public void setgetNombre(String nombres) {
        this.nombres = nombres;
    }

    public String toString(){
        return  nombres;
    }
}
