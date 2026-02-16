import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaIncidencias{

    public static void main(String[] args){
        List<Incidencia> incidencias = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while(!salir) {

            System.out.println("\n--- Sistema de registro de uncidencias---");
            System.out.println("1. Registrar incidencia");
            System.out.println("2. Listar incidencias");
            System.out.println("3. Buscar incidencias por palabra clave");
            System.out.println("0. Salir");
            System.out.println("Seleccione una opcion");

            String opcion = scanner.nextLine();

            switch (opcion) {

                case "1":
                    try {
                        System.out.println("Ingrese la descripcion: ");
                        String descripcion = scanner.nextLine();

                        System.out.println("Ingrese la fecha: ");
                        String fecha = scanner.nextLine();

                        System.out.print("Ingrese el nivel de prioridad (ALTA, MEDIA, BAJA): ");
                        String prioridad = scanner.nextLine();

                        Incidencia  nuevIncidencia = new Incidencia(descripcion, fecha, prioridad);

                        incidencias.add(nuevIncidencia);

                        System.out.println("Incidencia registrada exitosamente.");

                    }catch (DescripcionInvalidaException | PrioridadInvalidaException | FechaInvalidaException e){
                        System.out.println("Error: " e.getMessage());
                    }
                    break;

                    case "2":
                        if(incidencias.isEmpty()){
                            System.out.println("No hay incidencias registradas");
                        }else {
                            System.out.println("\n---Lista de incidencias---");
                            for(Incidencia inc : incidencias){
                                System.out.println(inc);
                            }
                        }
                        
                    break;

                    case "3":
                        System.out.print("Ingrese la clave para buscar: ");
                        String palabraClave = scanner.nextLine().trim().toLowerCase();

                        if(palabraClave.isEmpty()){
                            System.out.println("La palabra no puede estra vacia.");
                        }else {
                            boolean encontrado = false;

                            System.out.println("\n-----Resultados de busqueda-----");

                            for(Incidencia inc : incidencias){
                                if(inc.getDescripcion().toLowerCase().contains(palabraClave)){
                                    System.out.println(inc);
                                    encontrado = true;
                                }
                            }

                            if(!encontrado){
                                System.out.println("No se encontraron incidencias con esa palabra clave");
                            }
                        }
                        break;

                        case "0":
                            salir = true;
                            System.out.println("Saliendo del sistema...");

                            break;

                            default:
                                System.out.println("Opcion invalida. Intente de nuevo.");
                    

            }
        }

        scanner.close();
    }
}