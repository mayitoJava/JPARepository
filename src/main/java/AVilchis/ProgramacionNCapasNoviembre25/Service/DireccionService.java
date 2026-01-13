package AVilchis.ProgramacionNCapasNoviembre25.Service;

import AVilchis.ProgramacionNCapasNoviembre25.DAO.IDireccionJPARepository;
import AVilchis.ProgramacionNCapasNoviembre25.DAO.IUsuarioJPARepository;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Direccion;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Result;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DireccionService {

    @Autowired
    IDireccionJPARepository direccionJpaRepository;
    @Autowired
    IUsuarioJPARepository usuarioJpaRepository;

    @Transactional
    public Result add(Direccion direccion) {
        Result result = new Result();
        try {
            Usuario usuario = usuarioJpaRepository.findById(direccion.usuario.getIdUsuario()).get();
            direccion.usuario = usuario;
            direccionJpaRepository.save(direccion);
            result.Correct = true;

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    public Result update(Direccion direccion) {
        Result result = new Result();

        try {
            direccionJpaRepository.save(direccion);

        } catch (Exception ex) {
        }
        return result;
    }

    @Transactional
    public Result getById(int idDireccion) {
        Result result = new Result();
        try {
            Optional<Direccion> direccion = direccionJpaRepository.findById(idDireccion);
            result.Object = direccion.get();
            result.Correct = true;

        } catch (Exception e) {
            result.Correct = false;
            result.ErrorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Transactional
    public Result delete(int idDireccion) {
        Result result = new Result();
        try {
            Direccion direccion = direccionJpaRepository.findById(idDireccion).get();
            direccionJpaRepository.delete(direccion);
            result.Correct = true;

        } catch (Exception ex) {
            result.Correct = false;
            result.ex = ex;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return result;
    }
    
}
