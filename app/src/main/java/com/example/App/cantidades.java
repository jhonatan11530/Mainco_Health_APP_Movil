package com.example.App;

@SuppressWarnings("ALL")
public class cantidades {
    private String tarea;

    public cantidades() {

    }

    public cantidades(String tarea) {
        this.tarea = tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String toString() {
        return tarea;
    }
}
