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
        Actividad  actividad5 = new Actividad("Actividad 1", convertirADate("2:00"), convertirADate("5:00"));
        Actividad  actividad4 = new Actividad("Actividad 2", convertirADate("5:00"), convertirADate("7:00"));
        Actividad  actividad3 = new Actividad("Actividad 3", convertirADate("8:00"), convertirADate("20:00"));
        Actividad  actividad2 = new Actividad("Actividad 4", convertirADate("22:00"), convertirADate("23:00"));
        Actividad  actividad1 = new Actividad("Actividad 5", convertirADate("23:00"), convertirADate("24:00"));
        List<Actividad> listaActividades = new ArrayList<Actividad>(List.of(actividad1, actividad2, actividad3, actividad4, actividad5));
        */

        //generarActividades(30);

        List<Actividad> listacargadaActividades = new ArrayList<Actividad>(cargar());
        Robot robotBase = new Robot(0, 0, new ArrayList<Actividad>());
        Robot robotOptimo = new Robot(0, 0, new ArrayList<Actividad>());
        //Variables para determinar el tiempo de ejecución
        long tiempoInicio, tiempoFin, tiempo;
  
        ///*  Algoritmo 1 del Problema 1 Todas las posibles combinaciones Muy Costosa
        tiempoInicio = System.currentTimeMillis(); 
        Robot resultadoAlgoritmo1Problema1 = new Robot(robotOptimo);
        algoritmo1Problema1(new Robot(robotBase), new ArrayList<Actividad>(listacargadaActividades), false, resultadoAlgoritmo1Problema1);
        tiempoFin = System.currentTimeMillis();
        tiempo = tiempoFin - tiempoInicio;
        mostrar("Problema 1 Algoritmo 1: \n" + resultadoAlgoritmo1Problema1.toString() + "\nTiempo de ejecución en ms: " + tiempo + "\n-----------------------------");
        guardarProblema1("Algoritmo1", resultadoAlgoritmo1Problema1);
        //*/

        ///* Algoritmo 2 del Problema 1 Solución Voraz
        tiempoInicio = System.currentTimeMillis(); 
        Robot resultadoAlgoritmo2Problema1 = new Robot(robotOptimo);
        algoritmo2Problema1(new Robot(robotBase), new ArrayList<Actividad>(listacargadaActividades), resultadoAlgoritmo2Problema1, convertirADate("00:00"));
        tiempoFin = System.currentTimeMillis();
        tiempo = tiempoFin - tiempoInicio;
        mostrar("Problema 1 Algoritmo 2: \n" + resultadoAlgoritmo2Problema1.toString() + "\nTiempo de ejecución en ms: " + tiempo + "\n-----------------------------");
        guardarProblema1("Algoritmo2", resultadoAlgoritmo2Problema1);
        //*/

        ///* Algoritmo 3 del Problema 1 Solución Voraz Que recorre de forma inversa
        tiempoInicio = System.currentTimeMillis(); 
        Robot resultadoAlgoritmo3Problema1 = new Robot(robotOptimo);
        algoritmo3Problema1(new Robot(robotBase), new ArrayList<Actividad>(listacargadaActividades), resultadoAlgoritmo3Problema1, convertirADate("24:00"));
        tiempoFin = System.currentTimeMillis();
        tiempo = tiempoFin - tiempoInicio;
        mostrar("Problema 1 Algoritmo 3: \n" + resultadoAlgoritmo3Problema1.toString() + "\nTiempo de ejecución en ms: " + tiempo + "\n-----------------------------");
        guardarProblema1("Algoritmo3", resultadoAlgoritmo3Problema1);
        //*/

        ///* Algoritmo 1 del Problema 2 Solución Voraz Que recorre de forma inversa
        tiempoInicio = System.currentTimeMillis(); 
        Robot resultadoAlgoritmo1Problema2 = new Robot(robotOptimo);
        algoritmo1Problema2(new Robot(robotBase), new ArrayList<Actividad>(listacargadaActividades), resultadoAlgoritmo1Problema2, convertirADate("00:00"));
        tiempoFin = System.currentTimeMillis();
        tiempo = tiempoFin - tiempoInicio;
        mostrar("Problema 2 Algoritmo 1: \n" + resultadoAlgoritmo1Problema2.toString() + "\nTiempo de ejecución en ms: " + tiempo + "\n-----------------------------");
        guardarProblema2("Algoritmo1", resultadoAlgoritmo1Problema2);
        //*/    
    }
    
    
    public static void algoritmo1Problema1(Robot robotbase, List<Actividad> listaActividades, Boolean detener, Robot robotoptimo){
        if(detener){
            if (robotbase.getHorasTotal() > robotoptimo.getHorasTotal()) {
                List<Actividad> actividadesRobotBase = robotbase.getActividades();
                robotoptimo.clear();
                for (Actividad actividad : actividadesRobotBase) {
                        robotoptimo.agregarActividad(actividad);
                }
                
            }
        }else{
            for (int index = 0; index < listaActividades.size(); index++) {
                if (!robotbase.existeElemento(listaActividades.get(index))) {   //Esto implica otro For recorriendo las actividades del Robot               
                        robotbase.agregarActividad(listaActividades.get(index)); 
                        List<Actividad> listasinultimaactividad = new ArrayList<Actividad>(listaActividades);
                        listasinultimaactividad.remove(index);
                        algoritmo1Problema1(robotbase, listasinultimaactividad, false, robotoptimo);
                        robotbase.eliminarElemento(listaActividades.get(index));
                    } else {
                        algoritmo1Problema1(robotbase, listaActividades, true, robotoptimo);
                    }
                }
            }
        }

    public static void algoritmo2Problema1(Robot robotbase, List<Actividad> listaActividades, Robot robotoptimo ,Date horaComprobar) throws Exception{
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
                algoritmo2Problema1(robotbase, listaActividades, robotoptimo, horaComprobar);
            } else {
                horaComprobar = new Time(new Date(horaComprobar.getTime() + (1* 3600000)).getTime());
                algoritmo2Problema1(robotbase, listaActividades, robotoptimo, horaComprobar);
            }
        }
    }

    public static void algoritmo3Problema1(Robot robotbase, List<Actividad> listaActividades, Robot robotoptimo ,Date horaComprobar) throws Exception{
        if(horaComprobar.equals(convertirADate("00:00"))) {
                robotoptimo.clear();
                for (Actividad actividad : robotbase.getActividades()) {
                        robotoptimo.agregarActividad(actividad);
                }
        } else {
            List<Actividad> actividadesSeleccionadas = new ArrayList<>();
            for(int index=0; index < listaActividades.size(); index++){
                if(listaActividades.get(index).getHoraFin().equals(horaComprobar)){
                    actividadesSeleccionadas.add(listaActividades.get(index));
                    listaActividades.remove(index);
                }
            }
            if(!actividadesSeleccionadas.isEmpty()){
                Actividad actividadLarga = new Actividad("", horaComprobar, convertirADate("0:00"));
                for(Actividad actividad : actividadesSeleccionadas){
                    if(actividad.getHoraInicio().getTime() < actividadLarga.getHoraInicio().getTime()){
                        actividadLarga = actividad;
                    }
                }
                robotbase.agregarActividad(actividadLarga);
                horaComprobar = actividadLarga.getHoraInicio();
                algoritmo3Problema1(robotbase, listaActividades, robotoptimo, horaComprobar);
            } else {
                horaComprobar = new Time(new Date(horaComprobar.getTime() - (1* 3600000)).getTime());
                algoritmo3Problema1(robotbase, listaActividades, robotoptimo, horaComprobar);
            }
        }
    }

    public static void algoritmo1Problema2(Robot robotbase, List<Actividad> listaActividades, Robot robotoptimo ,Date horaComprobar) throws Exception{
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
                Actividad actividadcorta = new Actividad("", horaComprobar, convertirADate("24:00"));
                for(Actividad actividad : actividadesSeleccionadas){
                    if(actividad.getHoraFin().getTime() < actividadcorta.getHoraFin().getTime() || actividadcorta.getNombre() == ""){
                        actividadcorta = actividad;
                    }
                }
                robotbase.agregarActividad(actividadcorta);
                horaComprobar = actividadcorta.getHoraFin();
                algoritmo1Problema2(robotbase, listaActividades, robotoptimo, horaComprobar);
            } else {
                horaComprobar = new Time(new Date(horaComprobar.getTime() + (1* 3600000)).getTime());
                algoritmo1Problema2(robotbase, listaActividades, robotoptimo, horaComprobar);
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
                a = (int)(Math.random()*24+1);
                b = (int)(Math.random()*a);
                bw.write("Actividad"+Integer.toString(index+1) + " " + b + ":00 " + a + ":00");
                bw.newLine();
            }
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void guardarProblema1(String rotulo, Robot robotOptimo){
        try{
            String path = "problema1" + rotulo + ".txt";
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

    public static void guardarProblema2(String rotulo, Robot robotOptimo){
        try{
            String path = "problema2" + rotulo + ".txt";
            File file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(robotOptimo.getActividades().size()));
            bw.newLine();
            for(int index=0; index < robotOptimo.getActividades().size(); index++){
                bw.write(robotOptimo.getActividades().get(index).toString());
                bw.newLine();
            }
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
