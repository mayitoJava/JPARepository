package AVilchis.ProgramacionNCapasNoviembre25.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class Usuario {

    private int IdUsuario;
    @NotEmpty(message = "Campo requerido")
    @Pattern(regexp = "^(?=.*[0-9])[A-Z][a-zA-Z0-9(#$%&)]+$", message = "Ingresa campos validos")
    private String Username;

    @NotEmpty(message = "Campo necesario")
    @Size(min = 2, max = 20, message = "El tamaño minimo es de 2 y maximo 20")
    private String Nombre;

    @NotEmpty(message = "Campo necesario")
    @Size(min = 1, max = 20, message = "El tamaño minimo es de 1 y maximo 20")
    private String ApellidoPaterno;

    @NotEmpty(message = "Campo necesario")
    @Size(min = 1, max = 20, message = "El tamaño minimo es de 1 y maximo 20")
    private String ApellidoMaterno;

    @NotEmpty(message = "Campo requerido")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Ingresa email valido")
    private String Email;

    @NotEmpty(message = "Campo requerido")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "Ingresa contraseña valida")
    private String Password;

    @NotNull(message = "Campo requerido")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date FechaNacimiento;

    @NotNull(message = "Campo requerido")
    @Pattern(regexp = "^(M|F)$", message = "Selecciona una opción válida")
    private String Sexo;

    @NotNull(message = "Campo requerido")
    @Pattern(regexp = "^\\d{10}$", message = "Numero telefonico erroneo")
    private String Telefono;

    @NotEmpty(message = "Campo requerido")
    @Pattern(regexp = "^[A-Z]{4}\\d{6}[HM][A-Z]{2}[B-DF-HJ-NP-TV-Z]{3}[A-Z0-9]\\d$", message = "CURP erronea")
    private String Curp;

    @NotEmpty(message = "Campo requerido")
    @Pattern(regexp = "^\\d{10}$", message = "Celular erroneo")
    private String Celular;
    @Valid
    public Rol Rol;
    public List<Direccion> Direcciones;

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getCurp() {
        return Curp;
    }

    public void setCurp(String Curp) {
        this.Curp = Curp;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }

    public Rol getRol() {
        return Rol;
    }

    public void setRol(Rol Rol) {
        this.Rol = Rol;
    }
    
}
