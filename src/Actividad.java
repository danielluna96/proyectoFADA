public class Actividad {
    private String nombre;
    private int horaInicio;
    private int horaFin;

    public Actividad(String nombre, int horaIncio, int horaFin){
        this.nombre = nombre;
        this.horaInicio = horaIncio;
        this.horaFin = horaFin;
    }

    public int getHoraInicio() {
        return this.horaInicio;
    }
    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFin() {
        return this.horaFin;
    }
    public void setHoraFin(int horaFin) {
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
        cadena += getNombre();
        return cadena;
    }
    
}
