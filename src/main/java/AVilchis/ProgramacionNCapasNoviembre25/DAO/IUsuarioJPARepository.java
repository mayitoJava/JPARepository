package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioJPARepository extends JpaRepository<Usuario, Integer>{
    
}
