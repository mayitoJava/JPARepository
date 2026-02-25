package AVilchis.ProgramacionNCapasNoviembre25.Controller;

import AVilchis.ProgramacionNCapasNoviembre25.JPA.Direccion;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Rol;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario;
import AVilchis.ProgramacionNCapasNoviembre25.Service.ColoniaService;
import AVilchis.ProgramacionNCapasNoviembre25.Service.DireccionService;
import AVilchis.ProgramacionNCapasNoviembre25.Service.EstadoService;
import AVilchis.ProgramacionNCapasNoviembre25.Service.MunicipioService;
import AVilchis.ProgramacionNCapasNoviembre25.Service.PaisService;
import AVilchis.ProgramacionNCapasNoviembre25.Service.RolService;
import AVilchis.ProgramacionNCapasNoviembre25.Service.UsuarioService;
import AVilchis.ProgramacionNCapasNoviembre25.Service.ValidationService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // sirve para mapear interacciones del usuario 
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private ValidationService validatorService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private PaisService paisService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private ColoniaService coloniaService;

    @GetMapping
    public String GetAll(Model model) {
        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result result = usuarioService.GetAll();
        model.addAttribute("usuarios", result.Object);
        Usuario usuarioBusqueda = new Usuario();
        usuarioBusqueda.Rol = new Rol();
        model.addAttribute("usuarioBusqueda", usuarioBusqueda);
        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result resultRoles = rolService.GetAll();
        model.addAttribute("rol", resultRoles.Object);
        return "UsuarioIndex";
    }

    @GetMapping("form")
    public String Form(Model model, RedirectAttributes redirectAttributes) {
        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result result = rolService.GetAll();
        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result resultPais = paisService.getAll();
        model.addAttribute("rol", result.Object);
        model.addAttribute("pais", resultPais.Object);
        model.addAttribute("usuario", new Usuario());

        return "UsuarioForm";
    }

    @PostMapping("add")
    public String addAlumnoDireccion(@Valid @ModelAttribute("Usuario") Usuario usuario, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (usuario.getIdUsuario() == 0 && usuario.Direcciones.get(0).getIdDireccion() == 0) { // agregar usuario direccion

            if (bindingResult.hasErrors()) {
                AVilchis.ProgramacionNCapasNoviembre25.JPA.Result result = rolService.GetAll();
                model.addAttribute("rol", result.Object);
                model.addAttribute("usuario", usuario);
                
                return "Usuario";
            } else {
                ModelMapper modelMapper = new ModelMapper();
                AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario usuarioJpa = modelMapper.map(usuario, AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario.class);
                AVilchis.ProgramacionNCapasNoviembre25.JPA.Result result = usuarioService.Add(usuarioJpa);
                
                if(!result.Correct){
                    model.addAttribute("Sucedio un error");
                    return "UsuarioForm";
                }
                
                redirectAttributes.addFlashAttribute("El usuario se agrego con exito");
            }

        } else if (usuario.getIdUsuario() > 0 && usuario.Direcciones == null) { // editar usuario
            try{
                usuario.setPassword(usuario.getPassword());
                usuario.Direcciones = new ArrayList<>();
                usuario.Direcciones.add(new Direccion());
                ModelMapper modelMapper = new ModelMapper();
                
                AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario userEditar = modelMapper.map(usuario, AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario.class);
                AVilchis.ProgramacionNCapasNoviembre25.JPA.Result result = usuarioService.Update(userEditar);
                
                if(result.Correct){
                    result.Object = "Se actualizo :)";
                } else {
                    result.Object = "No se actualizo :(";
                }
                redirectAttributes.addFlashAttribute("ResultUpdate", result);
            } catch (Exception ex){
                return "redirect:/usuario/detail/" + usuario.getIdUsuario();
            }
        } else if ((usuario.getIdUsuario() > 0 && usuario.Direcciones.get(0).getIdDireccion() < 0)) { // editar direccion
            ModelMapper modelMapper = new ModelMapper();
            AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario usuarioJpa = modelMapper.map(usuario, AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario.class);
            usuarioJpa.Direcciones.get(0).usuario = new AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario();
            usuarioJpa.Direcciones.get(0).usuario.setIdUsuario(usuario.getIdUsuario());
            
            AVilchis.ProgramacionNCapasNoviembre25.JPA.Result resultupdir = direccionService.add(usuarioJpa.Direcciones.get(0));
            
            return "redirect:/usuario/detail/" + usuario.getIdUsuario();

        } else if ((usuario.getIdUsuario() > 0 && usuario.Direcciones.get(0).getIdDireccion() == 0)) { // agregar direccion
            ModelMapper modelMapper = new ModelMapper();
            
            AVilchis.ProgramacionNCapasNoviembre25.JPA.Direccion direccionJpa = modelMapper.map(usuario.Direcciones.get(0), AVilchis.ProgramacionNCapasNoviembre25.JPA.Direccion.class);
            direccionJpa.usuario = new AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario();
            direccionJpa.usuario.setIdUsuario(usuario.getIdUsuario());
            
            AVilchis.ProgramacionNCapasNoviembre25.JPA.Result resultadddir = direccionService.add(direccionJpa);
            
            if(resultadddir.Correct){
                redirectAttributes.addFlashAttribute("resultAdd", resultadddir.Object);
            }
            return "redirect:/usuario/detail/" + usuario.getIdUsuario();
        }

        return "redirect:/usuario";
    }
    
    @GetMapping("delete/{IdUsuario}")
    public String Delete(@PathVariable("IdUsuario") int IdUsuario, RedirectAttributes redirectAttributes){
        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result resultDelete = usuarioService.Delete(IdUsuario);
        if (resultDelete.Correct){
            resultDelete.Object = "Fue eliminado: " + IdUsuario + "se elimino correctamente";
        } else {
            resultDelete.Object = "El usuario no se puede eliminar";
        }
        redirectAttributes.addFlashAttribute("resultDelete", resultDelete);
        return "redirect:/usuario";
    }
    
    @GetMapping("softDelete/{IdUsuario}/{status}")
    @ResponseBody
    public AVilchis.ProgramacionNCapasNoviembre25.JPA.Result SoftDelete(@PathVariable("IdUsuario") int IdUsuario, @PathVariable("status") int status, RedirectAttributes redirectAttributes){
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(IdUsuario);
        usuario.setStatus(status);
        
        ModelMapper modelMapper = new ModelMapper();
        
        AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario usuarioJPA = modelMapper.map(usuario, AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario.class);
        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result resultSoft = usuarioService.SoftDelete(usuarioJPA);
        
        return resultSoft;
    }   
    
//    @GetMapping("")
//    public String DeleteDireccion(){
//        return "UsuarioEditar";
//    }
    
    @GetMapping("detail/{IdUsuario}")
    public String Detail(@PathVariable("IdUsuario") int IdUsuario, Model model, RedirectAttributes redirectAttributes){
        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result result = usuarioService.getById(IdUsuario);
        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result resultRol = rolService.GetAll();
        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result resultPais = paisService.getAll();
        model.addAttribute("rol", resultRol.Object);
        model.addAttribute("pais", resultPais.Object);
        model.addAttribute("usuario", result.Object);
        model.addAttribute("Direccion", new Direccion());
        return "UsuarioEditar";
    }
    
//    @GetMapping("getEstadoByPais/{IdPais")
//    @ResponseBody
//    public AVilchis.ProgramacionNCapasNoviembre25.JPA.Result getEstadoByPais(@PathVariable int IdPais){
//        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result result = estadoService.getByPais(IdPais);
//        return result;
//    }
//    
//    @GetMapping("getMunicipioByEstado/{IdEstado}")
//    @ResponseBody
//    public AVilchis.ProgramacionNCapasNoviembre25.JPA.Result getMunicipioByEstado(@PathVariable("IdEstado") int IdEstado){
//        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result result = municipioService.getByEstado(IdEstado);
//        return result;
//    }
//    
//    @GetMapping("getColoniaByMunicipio/{IdMunicipio}")
//    @ResponseBody
//    public AVilchis.ProgramacionNCapasNoviembre25.JPA.Result getColoniaByMunicipio(@PathVariable("IdColonia") int IdColonia){
//        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result result = coloniaService.getByMuncipio(IdColonia);
//        return result;
//    }
    
}
