package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.ML.Colonia;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Direccion;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccion{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAllByIdDireccion (int IdDireccion){
        Result resultdir = new Result();
        try{
            resultdir.Correct = jdbcTemplate.execute("{CALL GetAllByIdDireccion(?, ?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdDireccion);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                while(resultSet.next()){
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    
                    resultdir.Object = direccion;
                }
                
                return true;
                
            });       
            
        }catch(Exception ex){
            resultdir.Correct = false;
            resultdir.ErrorMessage = ex.getLocalizedMessage();
            resultdir.ex = ex;
        }
        
        return resultdir;
    }
}
