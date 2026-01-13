package AVilchis.ProgramacionNCapasNoviembre25.Service;

import AVilchis.ProgramacionNCapasNoviembre25.DAO.IEstadoJPARepository;
import AVilchis.ProgramacionNCapasNoviembre25.DAO.IPaisJPARepository;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Estado;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Pais;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Result;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {

    @Autowired
    IEstadoJPARepository estadoJpaRepository;

    @Autowired
    IPaisJPARepository paisJpaRepository;

    public Result getByPais(int idPais) {
        Result result = new Result();
        try {
            Pais pais = paisJpaRepository.findById(idPais).get();
            List<Estado> estados = estadoJpaRepository.findAll();
            result.Objects = new ArrayList();
            for (Estado estado : estados) {
                if (estado.Pais.getIdPais() == pais.getIdPais()) {
                    result.Objects.add(estado);
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
