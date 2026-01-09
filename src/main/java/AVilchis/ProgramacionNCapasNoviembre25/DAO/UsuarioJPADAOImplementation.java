package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

//    @Autowired
//    private ModelMapper modelMapper;
    @Override
    public Result GetAll() {
        //JPQL - para consulta de datos 
        TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM usuarioJPA", Usuario.class);
        List<Usuario> usuarios = queryUsuario.getResultList(); // Aqui no pasa

        Result result = new Result();

        ModelMapper modelMapper = new ModelMapper();

        result.Objects = new ArrayList<>();
        for (Usuario usuario : usuarios) {

            AVilchis.ProgramacionNCapasNoviembre25.ML.Usuario usuarioML = modelMapper.map(usuario, AVilchis.ProgramacionNCapasNoviembre25.ML.Usuario.class);

            result.Objects.add(usuarioML);
        }

        return result;
    }

    @Transactional
    @Override
    public Result Add(Usuario usuario) {
        Result result = new Result();

        try {
            entityManager.persist(usuario);
            usuario.Direcciones.get(0).usuario = new Usuario();
            usuario.Direcciones.get(0).usuario.setIdUsuario(usuario.getIdUsuario());
            entityManager.persist(usuario.Direcciones.get(0));
            
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result Update(AVilchis.ProgramacionNCapasNoviembre25.ML.Usuario usuario) {
        Result result = new Result();
        try {
            // Verificar si existe en la base de datos
            Usuario usuarioDb = entityManager.find(Usuario.class, usuario.getIdUsuario());

            if (usuarioDb != null) {
                ModelMapper modelMapper = new ModelMapper();
                Usuario usuarioJPA = modelMapper.map(usuario, Usuario.class);
                // Copiar direcciones si es necesario
                usuarioJPA.Direcciones = usuarioDb.Direcciones;
                
                //actualizas usuariojpa
                 entityManager.merge(usuarioJPA);
            } 
            
            result.Correct = true;
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
       @Override
        public Result GetById(int IdUsuario) {
        Result result = new Result();
        
        Usuario usuarioBD = entityManager.find(Usuario.class,IdUsuario);
        
        ModelMapper modelMaperr = new ModelMapper();
        
        AVilchis.ProgramacionNCapasNoviembre25.ML.Usuario usuarioML = modelMaperr.map(usuarioBD, AVilchis.ProgramacionNCapasNoviembre25.ML.Usuario.class);
        
        result.Object = usuarioML;
        
        return result;
    }

}
