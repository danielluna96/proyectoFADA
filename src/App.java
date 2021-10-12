import java.io.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        /*
        Actividad  actividad1 = new Actividad("Actividad 1", 0, 5);
        Actividad  actividad2 = new Actividad("Actividad 2", 5, 7);
        Actividad  actividad3 = new Actividad("Actividad 3", 9, 20);
        Actividad  actividad4 = new Actividad("Actividad 4", 21, 22);
        Actividad  actividad5 = new Actividad("Actividad 5", 22, 23);
        List<Actividad> listaActividades = new ArrayList<Actividad>(List.of(actividad1, actividad2, actividad3, actividad4, actividad5));
        */

        List<Actividad> listacargadaActividades = new ArrayList<Actividad>(cargar());
        mostrar(listacargadaActividades.toString());
        guardarUno(listacargadaActividades, 100); //El número de horas 
        guardarDos(listacargadaActividades);
        //recursividad(listacargadaActividades, new ArrayList<Actividad>(List.of()));
    }
    
    public static void recursividad(List<Actividad> lista, List<Actividad> listaResultado){
        for(int index = 0; index < lista.size(); index++){
            if(lista.size() == 1){
                listaResultado.add(lista.get(index));
                mostrar(listaResultado.toString());
            }
            List<Actividad> lista2 = new ArrayList<Actividad>(lista);
            lista2.remove(index);
            List<Actividad> listaResultado2 = new ArrayList<Actividad>(listaResultado);
            listaResultado2.add(lista.get(index));
            recursividad(lista2, listaResultado2);
        }
    }

    public static void comprobar(Actividad actividaduno, Actividad actividaddos){
        //
    }

    public static void mostrar(String cadena){
        System.out.println(cadena);
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
                    String nombreActividad = ""; 
                    int horaInicio = 0;
                    int horaFin = 0;
                    parts = st.split(Pattern.quote(" "));
                    nombreActividad = parts[0];
                    horaInicio = Integer.parseInt(parts[1].substring(0,parts[1].indexOf(":")));
                    horaFin = Integer.parseInt(parts[2].substring(0,parts[1].indexOf(":")));
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

    public static void guardarUno(List<Actividad> listaActividades, int horas){
        try{
            String path="resultadoUno.txt";
            File file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(listaActividades.size()));
            bw.newLine();
            bw.write(Integer.toString(horas));
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
