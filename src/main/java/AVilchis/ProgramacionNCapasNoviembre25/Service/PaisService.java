package AVilchis.ProgramacionNCapasNoviembre25.Service;

import AVilchis.ProgramacionNCapasNoviembre25.DAO.IPaisJPARepository;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisService {
    
    @Autowired
    IPaisJPARepository paisJpaRepository;
    
    public Result getAll(){
        
        Result result = new Result();
        try {
            result.Object = paisJpaRepository.findAll();
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ex = ex;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return result;
    
    }
    
}
