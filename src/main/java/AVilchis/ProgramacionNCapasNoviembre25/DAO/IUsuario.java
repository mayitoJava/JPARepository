package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Usuario;
import java.util.List;

public interface IUsuario {
    
    public Result GetAll();
    
    public Result Add(Usuario usuario);
    
    public Result GetAllByIdUsuarioDireccion(int IdUsuario);
    
    public Result GetByIdUsuario(int IdUsuario);
    
    public Result AddAll(List<Usuario> usuarios);
    
     public Result GetAllDinamico(Usuario usuario);
    
}
