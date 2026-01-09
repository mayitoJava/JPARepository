package AVilchis.ProgramacionNCapasNoviembre25.Rest;

//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculadora")
public class CalculadoraController {

    @PostMapping("/suma")
    public double suma(@RequestBody OperacionDTO dto) {
        return dto.getA() + dto.getB();
    }

    @PostMapping("/resta")
    public double resta(@RequestBody OperacionDTO dto) {
        return dto.getA() - dto.getB();
    }

    @PostMapping("/multiplicacion")
    public double multiplicacion(@RequestBody OperacionDTO dto) {
        return dto.getA() * dto.getB();
    }

    @PostMapping("/division")
    public Object division(@RequestBody OperacionDTO dto) {
        if (dto.getB() == 0) {
            return "Error: no se puede dividir entre 0";
        }
        return dto.getA() / dto.getB();
    }
}


