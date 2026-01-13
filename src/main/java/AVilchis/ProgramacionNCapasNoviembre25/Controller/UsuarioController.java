package AVilchis.ProgramacionNCapasNoviembre25.Controller;

import AVilchis.ProgramacionNCapasNoviembre25.ML.Usuario;
import AVilchis.ProgramacionNCapasNoviembre25.Service.ColoniaService;
import AVilchis.ProgramacionNCapasNoviembre25.Service.DireccionService;
import AVilchis.ProgramacionNCapasNoviembre25.Service.EstadoService;
import AVilchis.ProgramacionNCapasNoviembre25.Service.MunicipioService;
import AVilchis.ProgramacionNCapasNoviembre25.Service.PaisService;
import AVilchis.ProgramacionNCapasNoviembre25.Service.RolService;
import AVilchis.ProgramacionNCapasNoviembre25.Service.UsuarioService;
import AVilchis.ProgramacionNCapasNoviembre25.Service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        model.addAttribute("usuarioBusqueda", new Usuario());
        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result resultRoles = rolService.GetAll();
        model.addAttribute("rol", resultRoles.Object);
        return "UsuarioIndex";
    }

    @GetMapping("form")
    public String Form(Model model, RedirectAttributes redirectAttributes) {
        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result result = rolService.GetAll();
        AVilchis.ProgramacionNCapasNoviembre25.JPA.Result resultPais = paisService.getAll();
        model.addAttribute("rol", result.Objects);
        model.addAttribute("pais", resultPais.Objects);
        model.addAttribute("usuario", new Usuario());

        return "UsuarioForm";
    }
    
}
