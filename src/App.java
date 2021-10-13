import java.io.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Date;

public class App {
    public static void main(String[] args) throws Exception {
        
        /*//Solo para realizar pruebas rapidas
        Actividad  actividad1 = new Actividad("Actividad 1", convertirADate("2:00"), convertirADate("5:00"));
        Actividad  actividad2 = new Actividad("Actividad 2", convertirADate("4:00"), convertirADate("22:00"));
        Actividad  actividad3 = new Actividad("Actividad 3", convertirADate("22:00"), convertirADate("24:00"));
        Actividad  actividad4 = new Actividad("Actividad 4", convertirADate("22:00"), convertirADate("23:00"));
        Actividad  actividad5 = new Actividad("Actividad 5", convertirADate("23:00"), convertirADate("24:00"));
        List<Actividad> listaActividades = new ArrayList<Actividad>(List.of(actividad1, actividad2, actividad3, actividad4, actividad5));
        */
                
        List<Actividad> listacargadaActividades = new ArrayList<Actividad>(cargar());
        Robot robotbase = new Robot(0, new ArrayList<Actividad>());
        Robot robotoptimo = new Robot(0, new ArrayList<Actividad>());
        algoritmo1(robotbase, listacargadaActividades, false, robotoptimo);
        mostrar(robotoptimo.toString());
        guardarUno(robotoptimo);
    }
    
    
    public static void algoritmo1(Robot robotbase, List<Actividad> listaActividades, Boolean detener, Robot robotoptimo){
        if(detener){
            if (robotbase.getHorasTotal() > robotoptimo.getHorasTotal()) {
                List<Actividad> actividadesRobotBase = robotbase.getActividades();
                robotoptimo.clear();
                for (Actividad actividad : actividadesRobotBase) {
                        robotoptimo.agregarActividad(actividad);
                }
            }
        }else{
            for (int i = 0; i < listaActividades.size(); i++) {
                if (!robotbase.existeElemento(listaActividades.get(i))) {                  
                        robotbase.agregarActividad(listaActividades.get(i));
                        List<Actividad> listasinultimaactividad = new ArrayList<Actividad>(listaActividades);
                        listasinultimaactividad.remove(i);
                        algoritmo1(robotbase, listasinultimaactividad, false, robotoptimo);
                        robotbase.eliminarElemento(listaActividades.get(i));
                    } else {
                        algoritmo1(robotbase, listaActividades, true, robotoptimo);
                    }
                }
            }
        }

    public static void mostrar(String cadena){
        System.out.println(cadena);
    }

    public static Date convertirADate(String cadena) throws Exception{
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = formatter.parse(cadena);
        Time ppstime = new Time(date.getTime());
        return ppstime;
    }
    
    public static ArrayList<Actividad> cargar(){
        ArrayList<Actividad> listaActividades = new ArrayList<Actividad>(List.of());
        try{
            File file = new File("entrada.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            int counter = 1;
            int cantidadActividades = 0;        
            while ((st = br.readLine()) != null){
                if(counter==1){
                    cantidadActividades = Integer.parseInt(st);
                }else if(counter-1 > cantidadActividades ){
                    break;
                }else{
                    String[] parts = {};
                    String nombreActividad = new String(); 
                    Date horaInicio = new Date();
                    Date horaFin = new Date();
                    parts = st.split(Pattern.quote(" "));
                    nombreActividad = parts[0];
                    horaInicio = convertirADate(parts[1]);
                    horaFin = convertirADate(parts[2]);
                    Actividad nuevaActividad = new Actividad(nombreActividad, horaInicio, horaFin);
                    listaActividades.add(nuevaActividad);
                }
                counter++;    
            }
            if(counter-1<cantidadActividades){
                mostrar("No hay suficientes actividades como las indicadas");
            }
            br.close();
    }
    catch(Exception e){
        System.out.println(e);
    }
    return listaActividades;
    }

    public static void guardarUno(Robot robotOptimo){
        try{
            String path="resultadoUno.txt";
            File file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(robotOptimo.listaActividades.size()));
            bw.newLine();
            bw.write(Integer.toString(robotOptimo.horasTotal));
            bw.newLine();
            for(int index=0; index < robotOptimo.listaActividades.size(); index++){
                bw.write(robotOptimo.listaActividades.get(index).toString());
                bw.newLine();
            }
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void guardarDos(List<Actividad> listaActividades){
        try{
            String path="resultadoDos.txt";
            File file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(listaActividades.size()));
            bw.newLine();
            for(int index=0; index < listaActividades.size(); index++){
                bw.write(listaActividades.get(index).toString());
                bw.newLine();
            }
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
