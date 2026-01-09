package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.ML.Estado;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements IEstado {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetEstadoByPais(int IdPais){
        Result resultEstado = new Result();
        try{
            resultEstado.Correct = jdbcTemplate.execute("{CALL GetEstadoByPais (?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdPais);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                resultEstado.Objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("Nombre"));
                    resultEstado.Objects.add(estado);
                }
                
                return true;
                
            });
        }catch (Exception ex){
            resultEstado.Correct = false;
            resultEstado.ErrorMessage = ex.getLocalizedMessage();
            resultEstado.ex = ex;
        }
        return resultEstado;
    }
    
}
