package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;

public interface IUsuarioJPA {
    public Result GetAll();
    
    public Result Add(Usuario usuario);
    
    public Result Update(AVilchis.ProgramacionNCapasNoviembre25.ML.Usuario usuario);
    
     public Result GetById(int IdUsuario);
}
