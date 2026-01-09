
package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;

public interface IDireccionJPA {
    public Result GetById(int IdDireccion);
    public Result Update(AVilchis.ProgramacionNCapasNoviembre25.ML.Direccion direccion);
}
