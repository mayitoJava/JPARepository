package AVilchis.ProgramacionNCapasNoviembre25.ML;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class Rol {
    @Max(value=10, message = "El valor debe estar dentro del rango de 1-10")
    @Min(value=1, message = "El valor debe estar dentro del rango de 1-10")
    private int IdRol;  // CamelCase 
    private String Nombre;

    public int getIdRol() {
        return IdRol;
    }

    public void setIdRol(int IdRol) {
        this.IdRol = IdRol;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
}
