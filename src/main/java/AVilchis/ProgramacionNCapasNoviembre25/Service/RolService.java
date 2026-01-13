package AVilchis.ProgramacionNCapasNoviembre25.Service;

import AVilchis.ProgramacionNCapasNoviembre25.DAO.IRolJPARepository;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {

    @Autowired
    IRolJPARepository rolJpaRepository;

    public Result GetAll() {
        Result result = new Result();
        try {
            result.Object = rolJpaRepository.findAll();
            result.Correct = true;

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
