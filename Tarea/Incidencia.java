import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Incidencia{
    private static int contadorId = 1;
    private int id;
    private String descripcion;
    private Date fechaRegistro;
    private String nivelPrioridad;

  //Constructor con validaciones
    public Incidencia(String descripcion, String fechaStr, String nivelPrioridad)
        throws DescripcionInvalidaException, PrioridadInvalidaException, FechaInvalidaException{
      
     
     //Validar descripcion
     if(descripcion == null || descripcion.trim().isEmpty()){
        throw new DescripcionInvalidaException("La descripcion no puede ser null, vacia o contener solo espacios.");
     }

     String descTrim = descripcion.trim();

     if(descTrim.length() < 10){
        throw new DescripcionInvalidaException(
                  "La descripcion debe tener al menos 10 caracteres reales (sin contar espacios al inicio y al final");

        
     }

     this.descripcion = descTrim;

     //Validar prioridad
     if(nivelPrioridad == null || nivelPrioridad.trim().isEmpty()){
         throw new PrioridadInvalidaException("El nivel de prioridad no puede ser null o estar vacio");
     }

     String prioridadUpper = nivelPrioridad.trim().toUpperCase();

     if(!prioridadUpper.equals("ALTA")&&
        !prioridadUpper.equals("MEDIA")&&
        !prioridadUpper.equals("BAJA"));{

            throw new PrioridadInvalidaException("El nivel de prioridad debe ser 'ALTA', 'MEDIA' o 'BAJA'");
        }

        this.nivelPrioridad = prioridadUpper;

        //validar fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         sdf.setLenient(false);

         try {
            Date fecha = sdf.parse(fechaStr);

            if(fecha.after(new Date())){
                throw new FechaInvalidaException("La fecha no puede ser futura");
            }

            this.fechaRegistro = fecha;



        }catch(ParseException e){
            throw new FechaInvalidaException("El formato de fecha debe ser (dd/MM/yyyy)");
        }

        this.id = contadorId++;

    }

    public int getId(){
        return id;
    }

    public String getDescripcion(){
        return descripcion;
    }
    
    public Date getFecharegistro(){
        return fechaRegistro;
    }

    public String getNivelPrioridad(){
        return nivelPrioridad;
    }

    @Override
    public String toString(){
        SimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "ID: " + id + "\nDescripcion: " + descripcion  + "\nFecha de registro: " + sdf.formant(fechaRegistro) +  "\nNivel de prioridad: " + nivelPrioridad + "\n";
    }

}