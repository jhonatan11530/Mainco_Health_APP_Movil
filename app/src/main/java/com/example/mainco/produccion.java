package com.example.mainco;

public class produccion {
    private String numero_id;

    public produccion (){

    }
    public produccion(String numero_id){
        this.numero_id = numero_id;
    }

    public String getId() {
        return numero_id;
    }

    public void setId(String numero_id) {
        this.numero_id = numero_id;
    }

    public String toString(){
        return  numero_id;
    }
}
