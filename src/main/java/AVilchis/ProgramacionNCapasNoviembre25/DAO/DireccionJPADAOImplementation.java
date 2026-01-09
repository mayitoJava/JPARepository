package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.JPA.Direccion;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionJPADAOImplementation implements IDireccionJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetById(int IdDireccion) {
        Result result = new Result();

        Direccion direccionBD = entityManager.find(Direccion.class, IdDireccion);

        ModelMapper modelMaperr = new ModelMapper();

        AVilchis.ProgramacionNCapasNoviembre25.ML.Direccion direccionML = modelMaperr.map(direccionBD, AVilchis.ProgramacionNCapasNoviembre25.ML.Direccion.class);

        result.Object = direccionML;

        return result;
    }

    @Transactional
    @Override
    public Result Update(AVilchis.ProgramacionNCapasNoviembre25.ML.Direccion direccion) {
        Result result = new Result();
        try {
            // Verificar si existe en la base de datos
            Direccion direccionDb = entityManager.find(Direccion.class, direccion.getIdDireccion());

            if (direccionDb != null) {
                ModelMapper modelMapper = new ModelMapper();
                // Cambié el nombre de la variable para evitar conflicto con la clase
                Direccion direccionToUpdate = modelMapper.map(direccion, Direccion.class);
                entityManager.merge(direccionToUpdate);
            }

            result.Correct = true;

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
