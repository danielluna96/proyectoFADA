import java.io.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Date;

public class App {
    public static void main(String[] args) throws Exception {
        
        /*//Solo para realizar pruebas rapidas
        Actividad  actividad5 = new Actividad("Actividad 1", convertirADate("2:00"), convertirADate("5:00"));
        Actividad  actividad4 = new Actividad("Actividad 2", convertirADate("5:00"), convertirADate("7:00"));
        Actividad  actividad3 = new Actividad("Actividad 3", convertirADate("8:00"), convertirADate("20:00"));
        Actividad  actividad2 = new Actividad("Actividad 4", convertirADate("22:00"), convertirADate("23:00"));
        Actividad  actividad1 = new Actividad("Actividad 5", convertirADate("23:00"), convertirADate("24:00"));
        List<Actividad> listaActividades = new ArrayList<Actividad>(List.of(actividad1, actividad2, actividad3, actividad4, actividad5));
        */

        //generarActividades(30);

        List<Actividad> listacargadaActividades = new ArrayList<Actividad>(cargar());
        Robot robotbase = new Robot(0, new ArrayList<Actividad>());
        Robot robotoptimo = new Robot(0, new ArrayList<Actividad>());
        //Variables para determinar el tiempo de ejecución
        long tiempoInicio, tiempoFin, tiempo;

        //Se toma el tiempo de inicio
        tiempoInicio = System.currentTimeMillis(); 
        //algoritmo1(robotbase, listacargadaActividades, false, robotoptimo);
        algoritmo1voraz(robotbase, listacargadaActividades, robotoptimo , convertirADate("00:00"));
        

        //Una vez realizado el calculo se procede a determinar la diferencia
        tiempoFin = System.currentTimeMillis();
        tiempo = tiempoFin - tiempoInicio;
        mostrar("Tiempo de ejecución en milisegundos: " + tiempo); 
 
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

    public static void algoritmo1voraz(Robot robotbase, List<Actividad> listaActividades, Robot robotoptimo ,Date horaComprobar) throws Exception{
        if(horaComprobar.equals(convertirADate("24:00"))) {
                robotoptimo.clear();
                for (Actividad actividad : robotbase.getActividades()) {
                        robotoptimo.agregarActividad(actividad);
                }
        } else {
            List<Actividad> actividadesSeleccionadas = new ArrayList<>();
            for(int index=0; index < listaActividades.size(); index++){
                if(listaActividades.get(index).getHoraInicio().equals(horaComprobar)){
                    actividadesSeleccionadas.add(listaActividades.get(index));
                    listaActividades.remove(index);
                }
            }
            if(!actividadesSeleccionadas.isEmpty()){
                Actividad actividadLarga = new Actividad("", convertirADate("00:00"), horaComprobar);
                for(Actividad actividad : actividadesSeleccionadas){
                    if(actividad.getHoraFin().getTime() > actividadLarga.getHoraFin().getTime()){
                        actividadLarga = actividad;
                    }
                }
                robotbase.agregarActividad(actividadLarga);
                horaComprobar = actividadLarga.getHoraFin();
                algoritmo1voraz(robotbase, listaActividades, robotoptimo, horaComprobar);
            } else {
                horaComprobar = new Time(new Date(horaComprobar.getTime() + (1* 3600000)).getTime());
                algoritmo1voraz(robotbase, listaActividades, robotoptimo, horaComprobar);
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

    public static void generarActividades(int n){
        try{
            String path="entrada.txt";
            File file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(Integer.toString(n));
            bw.newLine();
            for(int index=0; index < n; index++){
                int a, b;
                a = new Random().nextInt(1, 24+1);
                b = new Random().nextInt(0, a);
                bw.write("Actividad"+Integer.toString(index+1) + " " + b + ":00 " + a + ":00");
                bw.newLine();
            }
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
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
