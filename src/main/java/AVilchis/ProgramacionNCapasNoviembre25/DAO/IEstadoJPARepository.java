package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.JPA.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstadoJPARepository extends JpaRepository<Estado, Integer>{
    
}
