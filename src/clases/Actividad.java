package clases;

import java.util.ArrayList;
import java.util.Date;

public class Actividad {
    private String nombre;
    private Date horaInicio;
    private Date horaFin;

    public Actividad(String nombre, Date horaIncio, Date horaFin){
        this.nombre = nombre;
        this.horaInicio = horaIncio;
        this.horaFin = horaFin;
    }
    
    public Actividad(Actividad actividad) {
        this.nombre = actividad.getNombre();
        this.horaInicio = actividad.getHoraInicio();
        this.horaFin = actividad.getHoraFin();
    }

    public Date getHoraInicio() {
        return this.horaInicio;
    }
    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return this.horaFin;
    }
    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Muestra la mochila
     * @return 
     */
    public String toString() {
        String cadena = "";
        cadena += getNombre() + " | " + getHoraInicio().toString() + " | " + getHoraFin().toString();
        return cadena;
    }
    
}
