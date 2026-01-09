package AVilchis.ProgramacionNCapasNoviembre25.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemoController {
    
    @GetMapping("/HolaMundo")
    public String HolaMundo(){
        return "index";
    }

    @GetMapping("/Hola/{Nombre}")
    public String Hola(@PathVariable String Nombre, Model model){
        model.addAttribute("NombrePersona", Nombre);
        return "Hola";
    }
    
    @GetMapping("/Suma")
    public String Suma(@RequestParam int NumeroUno, @RequestParam int NumeroDos, Model model){
        int resultado =  NumeroUno + NumeroDos;
        model.addAttribute("Resultado", resultado);
        return "Resultado";
    }
    
    @GetMapping("/Resta")
    public String Resta(@RequestParam int NumeroUno, @RequestParam int NumeroDos, Model model){
        int resultado =  NumeroUno - NumeroDos;
        model.addAttribute("Resultado", resultado);
        return "Resultado";
    }
    
    @GetMapping("/Multiplicacion")
    public String Multiplicacion(@RequestParam int NumeroUno, @RequestParam int NumeroDos, Model model){
        int resultado =  NumeroUno * NumeroDos;
        model.addAttribute("Resultado", resultado);
        return "Resultado";
    }
}
