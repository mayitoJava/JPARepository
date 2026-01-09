package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.ML.Municipio;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementation implements IMunicipio{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetMunicipioByEstado(int IdEstado){
        Result resultMuni = new Result();
        try{
            resultMuni.Correct = jdbcTemplate.execute("{CALL GetMunicipioByEstado(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdEstado);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                resultMuni.Objects = new ArrayList<>(); 
                
                while(resultSet.next()){
                    Municipio municipio = new Municipio();
                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombre(resultSet.getString("Nombre"));
                    resultMuni.Objects.add(municipio);
                }
                
                return true;
                
            });
            
        }catch (Exception ex){
            resultMuni.Correct = false;
            resultMuni.ErrorMessage = ex.getLocalizedMessage();
            resultMuni.ex = ex;
        }
        
        return resultMuni;
    }
}
