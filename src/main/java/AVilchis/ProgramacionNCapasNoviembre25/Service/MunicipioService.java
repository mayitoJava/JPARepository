package AVilchis.ProgramacionNCapasNoviembre25.Service;

import AVilchis.ProgramacionNCapasNoviembre25.DAO.IEstadoJPARepository;
import AVilchis.ProgramacionNCapasNoviembre25.DAO.IMunicipioJPARepository;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Municipio;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Result;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MunicipioService {
    @Autowired
    IMunicipioJPARepository municipioJpaRepository;
    
    @Autowired
    IEstadoJPARepository estadoJpaRepository;
    
    public Result getByEstado(int idEstado){
    Result result = new Result();
        try {
            result.Objects = new ArrayList();
            for(Municipio municipio:  municipioJpaRepository.findAll()){
                if(municipio.Estado.getIdEstado() == idEstado){
                    result.Objects.add(municipio);
                
                }
            }
            result.Correct = true;
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ex = ex;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
    return result;
    }
    
}
