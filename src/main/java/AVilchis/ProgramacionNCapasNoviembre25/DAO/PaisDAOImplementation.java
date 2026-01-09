package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.ML.Pais;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaisDAOImplementation implements IPais{
   @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result resultpais = new Result();
        try {
            resultpais.Correct = jdbcTemplate.execute("{CALL GetAllPais(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                resultpais.Objects = new ArrayList();

                while (resultSet.next()) {
                    Pais pais = new Pais();
                    pais.setIdPais(resultSet.getInt("IdPais"));
                    pais.setNombre(resultSet.getString("Nombre"));

                    resultpais.Objects.add(pais);

                }

                return true;
            });
        } catch (Exception ex) {
            resultpais.Correct = false;
            resultpais.ErrorMessage = ex.getLocalizedMessage();
            resultpais.ex = ex;
        }
        return resultpais;
    }
}
