package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.ML.Colonia;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementation implements IColonia{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetColoniaByMunicipio(int IdMunicipio){
        Result resultCol = new Result();
        try{
            resultCol.Correct = jdbcTemplate.execute("{CALL GetColoniaByMunicipio(?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdMunicipio);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                resultCol.Objects = new ArrayList<>(); 
                
                 while(resultSet.next()){
                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombre(resultSet.getString("Nombre"));
                    resultCol.Objects.add(colonia);
                }
                
                return true;
                
            });
            
        }catch(Exception ex){
            resultCol.Correct = false;
            resultCol.ErrorMessage = ex.getLocalizedMessage();
            resultCol.ex = ex;
        }
        
        return resultCol;
    }
    
}
