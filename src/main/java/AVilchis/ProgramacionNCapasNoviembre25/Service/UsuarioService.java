package AVilchis.ProgramacionNCapasNoviembre25.Service;

import AVilchis.ProgramacionNCapasNoviembre25.DAO.IDireccionJPARepository;
import AVilchis.ProgramacionNCapasNoviembre25.DAO.IUsuarioJPARepository;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Result;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioJPARepository usuarioJPARepository;

    @Autowired
    private IDireccionJPARepository direccionJPARepository;

    @Transactional
    public Result GetAll() {
        Result result = new Result();
        try {
            result.Object = usuarioJPARepository.findAll(Sort.by("IdUsuario").descending());
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Transactional
    public Result Add(Usuario usuario){
        Result result = new Result();
        try {
            usuarioJPARepository.save(usuario);
            usuario.Direcciones.get(0).usuario = new Usuario();
            usuario.Direcciones.get(0).usuario.setIdUsuario(usuario.getIdUsuario());
            direccionJPARepository.save(usuario.Direcciones.get(0));
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Transactional
    public Result Update(Usuario usuario){
        Result result = new Result();
        try {
            Optional<Usuario> user = usuarioJPARepository.findById(usuario.getIdUsuario());
            usuario.Direcciones = user.get().Direcciones;
            usuario.setStatus(1);
            usuarioJPARepository.save(usuario);
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Transactional
    public Result Delete(int idUsuario){
        Result result = new Result();
        try {
            usuarioJPARepository.deleteById(idUsuario);
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Transactional
    public Result SoftDelete(Usuario usuario){
        Result result = new Result();
        try {
            Optional<Usuario> user = usuarioJPARepository.findById(usuario.getIdUsuario());
            user.get().setStatus(usuario.getStatus());
            usuario = user.get();
            usuarioJPARepository.save(usuario);
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Transactional
    public Result getById(int idUsuario){
        Result result = new Result();
        try {
            Optional<Usuario> usuario = usuarioJPARepository.findById(idUsuario);
            result.Object = usuario.get();
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    
    
//    public int Suma(int NumeroUno, int NumeroDos){
//        
//        return NumeroUno + NumeroDos;
//    }
//    
//    public int Suma(int Numerotres, int Numerocuatro, String Numerocinco){
//        
//        return Numerotres + Numerocuatro;
//    }
    
    
}
