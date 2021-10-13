import java.util.ArrayList;
import java.util.List;

public class Robot {
    
    int horasTotal = 0;
    List<Actividad> listaActividades = new ArrayList<Actividad>(List.of());

    public Robot(int horasTotal, List<Actividad> listaActividades){
        this.horasTotal = horasTotal;
        this.listaActividades = new ArrayList<Actividad>(listaActividades);
    }

    public int getHorasTotal(){
        return this.horasTotal;
    }

    public void setHorasTotal(int horasTotal){
        this.horasTotal = horasTotal;
    }

    public List<Actividad> getActividades(){
        return this.listaActividades;
    }

    public void clear(){
        this.listaActividades.clear();
        this.horasTotal = 0;
    }

    /**
     * Elimina elemento dado
     * @param e 
     */
    public void eliminarElemento(Actividad actividad) {
        for (int i = 0; i < this.listaActividades.size(); i++) {
            if (this.listaActividades.get(i).equals(actividad)) {
                this.listaActividades.remove(actividad); //el elemento fuera
                this.horasTotal -= (actividad.getHoraFin().getTime() - actividad.getHoraInicio().getTime())/3600000; //Reduce el peso
                break;
            }
        }
    }

    /**
     * Indica si existe un elemento
     * @param e
     * @return 
     */
    public boolean existeElemento(Actividad actividad) {
        for (int i = 0; i < this.listaActividades.size(); i++) {
            if (listaActividades.get(i).getHoraInicio().before(actividad.getHoraFin()) && actividad.getHoraInicio().before(listaActividades.get(i).getHoraFin())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Añade un elemento a la mochila
     * @param e 
     */
    public void agregarActividad(Actividad actividad) {
        this.listaActividades.add(actividad);
        this.horasTotal += (actividad.getHoraFin().getTime() - actividad.getHoraInicio().getTime())/3600000;
    }

    
        /**
     * Muestra la mochila
     * @return 
     */
    public String toString() {
        String cadena="";
        for (int index = 0; index < this.listaActividades.size(); index++) {
            if (this.listaActividades.get(index) != null) {
                cadena+=listaActividades.get(index).toString()+"\n";
            }
        }
        cadena+="Horas: " + getHorasTotal();
        return cadena;
    }

}
