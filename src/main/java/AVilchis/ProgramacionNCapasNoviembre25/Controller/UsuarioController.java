package AVilchis.ProgramacionNCapasNoviembre25.Controller;

import AVilchis.ProgramacionNCapasNoviembre25.DAO.ColoniaDAOImplementation;
import AVilchis.ProgramacionNCapasNoviembre25.DAO.DireccionDAOImplementation;
import AVilchis.ProgramacionNCapasNoviembre25.DAO.DireccionJPADAOImplementation;
import AVilchis.ProgramacionNCapasNoviembre25.DAO.EstadoDAOImplementation;
import AVilchis.ProgramacionNCapasNoviembre25.DAO.MunicipioDAOImplementation;
import AVilchis.ProgramacionNCapasNoviembre25.DAO.UsuarioDAOIMplementation;
import AVilchis.ProgramacionNCapasNoviembre25.DAO.RolDAOImplementation;
import AVilchis.ProgramacionNCapasNoviembre25.DAO.PaisDAOImplementation;
import AVilchis.ProgramacionNCapasNoviembre25.DAO.UsuarioJPADAOImplementation;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Direccion;
import AVilchis.ProgramacionNCapasNoviembre25.ML.ErrorCarga;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Rol;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Usuario;
import AVilchis.ProgramacionNCapasNoviembre25.Service.ValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller // sirve para mapear interacciones del usuario 
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDAOIMplementation usuarioDAOImplementation;

    @Autowired
    private RolDAOImplementation rolDAOImplementation;

    @Autowired
    private PaisDAOImplementation paisDAOImplementation;

    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;

    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;

    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;

    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    @Autowired
    private DireccionJPADAOImplementation direccionJPADAOImplementation;

    @Autowired
    private ValidationService validatorService;

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;

    @GetMapping // responder a interacciones de usuario
    public String GetAll(Model model) {
//        Result result = usuarioDAOImplementation.GetAll();
//        Result result = usuarioJPADAOImplementation.GetAll();        
//        model.addAttribute("usuarios", result.Objects);
//        model.addAttribute("usuarioBusqueda", new Usuario());
//        model.addAttribute("rol", rolDAOImplementation.GetAll().Objects);
          
        return "UsuarioIndex";
    }

    @GetMapping("form")
    public String Form(Model model) {
        Result resultpais = paisDAOImplementation.GetAll();
        model.addAttribute("pais", resultpais.Objects);
        Result result = rolDAOImplementation.GetAll();
        model.addAttribute("rol", result.Objects);

        Usuario usuario = new Usuario();
        usuario.Direcciones = new ArrayList<>();
        usuario.Direcciones.add(new Direccion());

        model.addAttribute("usuario", usuario);
        return "UsuarioForm";
    }

    @PostMapping("add")
    public String Add(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "UsuarioForm";
        } else {
            ModelMapper modelMapper = new ModelMapper();
            AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario usuarioJPA = modelMapper.map(usuario, AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario.class);
            Result resultAdd = usuarioJPADAOImplementation.Add(usuarioJPA);
            model.addAttribute("resultado", resultAdd);
        }
        return "UsuarioIndex";
    }

    @GetMapping("detail/{IdUsuario}")
    public String Detail(@PathVariable("IdUsuario") int IdUsuario, Model model) {
        Result resulto = usuarioDAOImplementation.GetAllByIdUsuarioDireccion(IdUsuario);
        model.addAttribute("Direccion", new Direccion());
        model.addAttribute("usuario", resulto.Objects);
        return "UsuarioEditar"; //retorna vistas
    }

    //GetALLPaises
    @GetMapping("/getPaises")
    @ResponseBody
    public Result GetAllpises() {

        Result resultPais = paisDAOImplementation.GetAll();

        return resultPais; //retorna datos
    }

    @GetMapping("GetEstadoByPais/{IdPais}")
    @ResponseBody
    public Result GetEstadoByPais(@PathVariable("IdPais") int IdPais) {
        Result result = estadoDAOImplementation.GetEstadoByPais(IdPais);
        return result;
    }

    @GetMapping("GetMunicipioByEstado/{IdEstado}")
    @ResponseBody
    public Result GetMunicipioByEstado(@PathVariable("IdEstado") int IdEstado) {
        Result result = municipioDAOImplementation.GetMunicipioByEstado(IdEstado);
        return result;
    }

    @GetMapping("GetColoniaByMunicipio/{IdMunicipio}")
    @ResponseBody
    public Result GetColoniaByMunicipio(@PathVariable("IdMunicipio") int IdMunicipio) {
        Result result = coloniaDAOImplementation.GetColoniaByMunicipio(IdMunicipio);
        return result;
    }

    @GetMapping("/formEditable") //Solo renderiza
    public String Form(@RequestParam("IdUsuario") int IdUsuario, @RequestParam(required = false) Integer IdDireccion, Model model) {

        if (IdDireccion == null) { // editar usuario
            Result result = usuarioDAOImplementation.GetByIdUsuario(IdUsuario);
            Result resultSemestres = rolDAOImplementation.GetAll();
            model.addAttribute("rol", resultSemestres.Objects);

            Usuario usuario = (Usuario) result.Object;
            usuario.Direcciones = new ArrayList<>();
            usuario.Direcciones.add(new Direccion());
            usuario.Direcciones.get(0).setIdDireccion(-1);

            model.addAttribute("usuario", usuario);
            return "UsuarioForm";
        } else if (IdDireccion == 0) { //Aregar direccion
            //Formulario de direccion sin datos

            Result result = usuarioDAOImplementation.GetByIdUsuario(IdUsuario);
            model.addAttribute("paises", paisDAOImplementation.GetAll().Objects);

            Usuario usuario = (Usuario) result.Object;

            if (usuario.Direcciones == null) {
                usuario.Direcciones = new ArrayList<>();
                Direccion direccion = new Direccion();
                direccion.setIdDireccion(0);  // nueva direccion
                usuario.Direcciones.add(direccion);
            }

            model.addAttribute("usuario", usuario);
            return "UsuarioForm";

        } else {// Editar Direccion
            //Retornar formulario direccion con datos              
            //Simulacion de DAOImplementation

            Result result = direccionDAOImplementation.GetAllByIdDireccion(IdDireccion);
            Direccion direccion = (Direccion) result.Object;

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(IdUsuario);

            usuario.Direcciones = new ArrayList<>();
            usuario.Direcciones.add(direccion);

            model.addAttribute("Paises", paisDAOImplementation.GetAll().Objects);
            model.addAttribute("usuario", usuario);

            return "UsuarioForm";
        }
    }

//    @PostMapping("/formEditable")
//    public String Form(@ModelAttribute Usuario usuario) {
//        if (usuario.getIdUsuario() == 0) {
//            // Añadir Usuario
//            ModelMapper modelMapper = new ModelMapper();
//            AVilchis.ProgramacionNCapasNoviembre25.JPA.usuarioJPA usuarioJPA = modelMapper.map(usuario, AVilchis.ProgramacionNCapasNoviembre25.JPA.usuarioJPA.class);
//            Result resultadd = usuarioJPADAOImplementation.Add(usuarioJPA);
//        } else if (usuario.Direcciones.get(0).getIdDireccion() == -1) {
//            //Actualizar usuario
//            Result resultupdate = usuarioJPADAOImplementation.Update(usuario);
//            if (resultupdate.Correct) {
//                System.out.println("Actualización exitosa");
//            } else {
//                System.out.println("Error: " + resultupdate.ErrorMessage);
//            }
//        } else if (usuario.Direcciones.get(0).getIdDireccion() == 0) {
//            //Añadir Direccion
//        } else {
//            //Actualizr Direccion
//            Result resultupdatedireccion = direccionJPADAOImplementation.Update(usuario.Direcciones.get(0));
//            if (resultupdatedireccion.Correct) {
//                System.out.println("Actualización exitosa");
//            } else {
//                System.out.println("Error: " + resultupdatedireccion.ErrorMessage);
//            }
//
//        }
//        return "redirect:/usuario";
//    }
    // Guardar o actualizar usuario
    @PostMapping("/formEditable")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        Result result;

        if (usuario.getIdUsuario() == 0) {
            // Crear nuevo usuario
            ModelMapper modelMapper = new ModelMapper();
            AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario usuarioJPA
                    = modelMapper.map(usuario, AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario.class);
            result = usuarioJPADAOImplementation.Add(usuarioJPA);

            if (result.Correct) {
                System.out.println("Usuario agregado correctamente");
            } else {
                System.out.println("Error al agregar usuario: " + result.ErrorMessage);
            }

        } else {
            // Actualizar usuario existente
            result = usuarioJPADAOImplementation.Update(usuario);

            if (result.Correct) {
                System.out.println("Usuario actualizado correctamente");
            } else {
                System.out.println("Error al actualizar usuario: " + result.ErrorMessage);
            }
        }

        return "redirect:/usuario"; // Redirige al listado de usuarios
    }

    // Guardar o actualizar dirección (JSON)
    @PostMapping("/GuardarDireccion")
    @ResponseBody
    public Result guardarDireccion(@ModelAttribute Direccion direccion) {
        Map<String, Object> response = new HashMap<>();
        Result result = new Result();
        System.out.println(direccion.getIdDireccion());
        System.out.println(direccion.getCalle());

        if (direccion.getIdDireccion() == 0) {
            //Crear nueva dirección
            //result = direccionJPADAOImplementation.Add(direccion);
            System.out.println("Debugg 0");
        } else {
            // Actualizar dirección existente
            System.out.println("debug 1");
            result = direccionJPADAOImplementation.Update(direccion);
        }

        if (result.Correct) {
            response.put("success", true);
            response.put("message", "Dirección guardada correctamente");
        } else {
            response.put("success", false);
            response.put("message", result.ErrorMessage);
        }
        return result;
    }

    @GetMapping("CargaMasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("CargaMasiva")
    public String CargaMasiva(@ModelAttribute MultipartFile archivo, Model model, HttpSession session) throws IOException {
        String extencion = archivo.getOriginalFilename().split("\\.")[1];

        String path = System.getProperty("user.dir");
        String pathArchivo = "src\\main\\resources\\archivos";
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String rutaabsoluta = path + "/" + pathArchivo + "/" + fecha + archivo.getOriginalFilename();

        archivo.transferTo(new File(rutaabsoluta));
        List<Usuario> usuarios = new ArrayList<>();

        if (extencion.equals("txt")) {
            usuarios = LecturaArchivo(new File(rutaabsoluta));
        } else {
            //archivo xlss
            usuarios = LecturaArchivoExcel(new File(rutaabsoluta));
        }
        List<ErrorCarga> error = ValidarDatosTxt(usuarios);
        if (error.isEmpty()) {
            model.addAttribute("listaErrores", error);
            session.setAttribute("archivoCargaMasiva", rutaabsoluta);
        } else {
            model.addAttribute("listaErrores", error);
            //retornar la lista de errores a la vista
        }
        return "CargaMasiva";
    }

    public List<Usuario> LecturaArchivo(File archivo) {
        List<Usuario> usuarios = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(archivo); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            bufferedReader.readLine();
            String line = " ";
            while ((line = bufferedReader.readLine()) != null) {
                String[] datos = line.split("\\|");
                Usuario usuario = new Usuario();
                usuario.setUsername(datos[0]);
                usuario.setNombre(datos[1]);
                usuario.setApellidoPaterno(datos[2]);
                usuario.setApellidoMaterno(datos[3]);
                usuario.setEmail(datos[4]);
                usuario.setPassword(datos[5]);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date fecha = sdf.parse(datos[6]);
                usuario.setFechaNacimiento(fecha);
                usuario.setSexo(datos[7]);
                usuario.setTelefono(datos[8]);
                usuario.setCelular(datos[9]);
                usuario.setCurp(datos[10]);
                usuario.Rol = new Rol();
                usuario.Rol.setIdRol(Integer.parseInt(datos[11]));
                usuarios.add(usuario);
            }
        } catch (Exception ex) {
            return null;
        }
        return usuarios;
    }

    public List<Usuario> LecturaArchivoExcel(File archivo) {
        List<Usuario> usuarios = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Usuario usuario = new Usuario();
                usuario.setUsername(row.getCell(0).toString());
                usuario.setNombre(row.getCell(1).toString());
                usuario.setApellidoPaterno(row.getCell(2).toString());
                usuario.setApellidoMaterno(row.getCell(3).toString());
                usuario.setEmail(row.getCell(4).toString());
                usuario.setPassword(row.getCell(5).toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date fecha = sdf.parse(row.getCell(6).toString());
                usuario.setFechaNacimiento(fecha);
                usuario.setSexo(row.getCell(7).toString());
                usuario.setTelefono(row.getCell(8).toString());
                usuario.setCelular(row.getCell(9).toString());
                usuario.setCurp(row.getCell(10).toString());
                usuario.setRol(new Rol());
                int idRol = (int) row.getCell(11).getNumericCellValue();
                usuario.getRol().setIdRol(idRol);
                usuarios.add(usuario);
            }
        } catch (Exception ex) {
            usuarios = null;
        }
        return usuarios;
    }

    //validacion
    public List<ErrorCarga> ValidarDatosTxt(List<Usuario> usuarios) {
        List<ErrorCarga> erroresCarga = new ArrayList<>();
        int LineaError = 0;
        for (Usuario usuario : usuarios) {
            List<ObjectError> errors = new ArrayList<>();
            LineaError++;
            BindingResult bindingResultUsuario = validatorService.validateObjects(usuario);
            if (bindingResultUsuario.hasErrors()) {
                errors.addAll(bindingResultUsuario.getAllErrors());
            }
            if (usuario.Rol != null) {
                BindingResult bindingRol = validatorService.validateObjects(usuario.Rol);
                if (bindingRol.hasErrors()) {
                    errors.addAll(bindingRol.getAllErrors());
                }
            }
            for (ObjectError error : errors) {
                FieldError fieldError = (FieldError) error;
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.linea = LineaError;
                errorCarga.Campo = fieldError.getField();
                errorCarga.Descripcion = fieldError.getDefaultMessage();
                erroresCarga.add(errorCarga);
            }
        }
        return erroresCarga;
    }

    @GetMapping("CargaMasiva/procesar")
    public String ProcesarArchivo(HttpSession sesion, Model model) {
        String path = sesion.getAttribute("archivoCargaMasiva").toString();
        String extencion = new File(path).getName().split("\\.")[1];
        Result result;

        if (extencion.equals("txt")) {
            List<Usuario> usuarios = LecturaArchivo(new File(path));
            result = usuarioDAOImplementation.AddAll(usuarios);
            model.addAttribute("usuario", result.Objects);
        } else {
            List<Usuario> usuarios = LecturaArchivoExcel(new File(path));
            result = usuarioDAOImplementation.AddAll(usuarios);
            model.addAttribute("usuario", result.Objects);
        }
        sesion.removeAttribute("archivoCargaMasiva");
        new File(path).delete();

        return "UsuarioIndex";
    }

    @PostMapping("/GetAllDinamico")
    public String GetAllDinamico(@ModelAttribute Usuario usuario, Model model) {

        model.addAttribute("usuarioBusqueda", new Usuario());
        model.addAttribute("rol", rolDAOImplementation.GetAll().Objects);
        model.addAttribute("usuarios", usuarioDAOImplementation.GetAllDinamico(usuario).Objects);

        return "UsuarioIndex";
    }

    @GetMapping("/GetByIdDireccion/{IdDireccion}")
    @ResponseBody
    public Result GetByIdDireccion(@PathVariable int IdDireccion) {

        Result result = direccionJPADAOImplementation.GetById(IdDireccion);
        Result resultPaises = paisDAOImplementation.GetAll();

        result.Objects = resultPaises.Objects;

        return result;
    }

    @GetMapping("/GetByIdUsuario/{IdUsuario}")
    @ResponseBody
    public Result GetByIdUsuario(@PathVariable int IdUsuario) {

        Result result = usuarioJPADAOImplementation.GetById(IdUsuario);

        return result;
    }

}
