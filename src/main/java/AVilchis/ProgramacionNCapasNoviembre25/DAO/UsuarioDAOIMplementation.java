package AVilchis.ProgramacionNCapasNoviembre25.DAO;

import AVilchis.ProgramacionNCapasNoviembre25.ML.Direccion;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Municipio;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Colonia;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Estado;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Pais;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Usuario;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Rol;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOIMplementation implements IUsuario {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {

        Result result = new Result();

        try {
            result.Correct = jdbcTemplate.execute("{CALL UsuarioDireccionGetAll(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.Objects = new ArrayList<>();

                while (resultSet.next()) {
                    int IdUsuarioPorIngresar = resultSet.getInt("IdUsuario");
                    if (!result.Objects.isEmpty() && ((Usuario) result.Objects.get(result.Objects.size() - 1)).getIdUsuario() == IdUsuarioPorIngresar) {
                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                        Usuario usuario = ((Usuario) result.Objects.get(result.Objects.size() - 1));
                        usuario.Direcciones.add(direccion);
                    } else {
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(IdUsuarioPorIngresar);
                        usuario.setUsername(resultSet.getString("Username"));
                        usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setPassword(resultSet.getString("Password"));
                        usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuario.setSexo(resultSet.getString("Sexo"));
                        usuario.setTelefono(resultSet.getString("Telefono"));
                        usuario.setCurp(resultSet.getString("Curp"));
                        usuario.setCelular(resultSet.getString("Celular"));
                        int IdDireccion = resultSet.getInt("IdDireccion");
                        if (IdDireccion != 0) {
                            usuario.Direcciones = new ArrayList<>();
                            Direccion direccion = new Direccion();
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            direccion.Colonia = new Colonia();
                            direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                            direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                            direccion.Colonia.Municipio = new Municipio();
                            direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                            direccion.Colonia.Municipio.Estado = new Estado();
                            direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                            direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                            direccion.Colonia.Municipio.Estado.Pais = new Pais();
                            direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                            direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                            usuario.Direcciones.add(direccion);
                        }
                        result.Objects.add(usuario);
                    }
                }
                return true;
            });

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result Add(Usuario usuario) {
        Result result = new Result();

        try {
            result.Correct = jdbcTemplate.execute("{CALL UsuarioDireccion(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setString(1, usuario.getUsername());
                callableStatement.setString(2, usuario.getNombre());
                callableStatement.setString(3, usuario.getApellidoPaterno());
                callableStatement.setString(4, usuario.getApellidoMaterno());
                callableStatement.setString(5, usuario.getEmail());
                callableStatement.setString(6, usuario.getPassword());
                java.sql.Date sqlDate = new java.sql.Date(usuario.getFechaNacimiento().getTime());
                callableStatement.setDate(7, sqlDate);
                callableStatement.setString(8, usuario.getSexo());
                callableStatement.setString(9, usuario.getTelefono());
                callableStatement.setString(10, usuario.getCurp());
                callableStatement.setString(11, usuario.getCelular());
                callableStatement.setString(12, usuario.Direcciones.get(0).getCalle());
                callableStatement.setString(13, usuario.Direcciones.get(0).getNumeroInterior());
                callableStatement.setString(14, usuario.Direcciones.get(0).getNumeroExterior());
                callableStatement.executeUpdate();
                return true;
            });
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetAllByIdUsuarioDireccion(int IdUsuario) {
        Result resulto = new Result();
        try {
            resulto.Correct = jdbcTemplate.execute("{CALL GetAllByIdUsuarioDireccion(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdUsuario);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                resulto.Objects = new ArrayList<>();

                while (resultSet.next()) {
                    int IdUsuarioPorIngresar = resultSet.getInt("IdUsuario");

                    Usuario usuario;
                    if (!resulto.Objects.isEmpty()
                            && ((Usuario) resulto.Objects.get(resulto.Objects.size() - 1)).getIdUsuario() == IdUsuarioPorIngresar) {
                        usuario = (Usuario) resulto.Objects.get(resulto.Objects.size() - 1);
                    } else {
                        usuario = new Usuario();
                        usuario.setIdUsuario(IdUsuarioPorIngresar);
                        usuario.setUsername(resultSet.getString("Username"));
                        usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setPassword(resultSet.getString("Password"));
                        usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuario.setSexo(resultSet.getString("Sexo"));
                        usuario.setTelefono(resultSet.getString("Telefono"));
                        usuario.setCurp(resultSet.getString("Curp"));
                        usuario.setCelular(resultSet.getString("Celular"));
                        usuario.Direcciones = new ArrayList<>();

                        resulto.Objects.add(usuario);
                    }

                    int IdDireccion = resultSet.getInt("IdDireccion");
                    if (IdDireccion != 0) {
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                        Colonia colonia = new Colonia();
                        colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        colonia.setNombre(resultSet.getString("NombreColonia"));
                        colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                        Municipio municipio = new Municipio();
                        municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        municipio.setNombre(resultSet.getString("NombreMunicipio"));

                        Estado estado = new Estado();
                        estado.setIdEstado(resultSet.getInt("IdEstado"));
                        estado.setNombre(resultSet.getString("NombreEstado"));

                        Pais pais = new Pais();
                        pais.setIdPais(resultSet.getInt("IdPais"));
                        pais.setNombre(resultSet.getString("NombrePais"));

                        estado.Pais = pais;
                        municipio.Estado = estado;
                        colonia.Municipio = municipio;
                        direccion.Colonia = colonia;

                        usuario.Direcciones.add(direccion);
                    }
                }

                return true;
            });
        } catch (Exception ex) {
            resulto.Correct = false;
            resulto.ErrorMessage = ex.getLocalizedMessage();
            resulto.ex = ex;
        }
        return resulto;
    }

    @Override
    public Result GetByIdUsuario(int IdUsuario) {
        Result resultuser = new Result();
        try {
            resultuser.Correct = jdbcTemplate.execute("{CALL GetByIdUsuario(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdUsuario);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                while (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuario.setUsername(resultSet.getString("Username"));
                    usuario.setNombre(resultSet.getString("Nombre"));
                    usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    usuario.setEmail(resultSet.getString("Email"));
                    usuario.setPassword(resultSet.getString("Password"));
                    usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                    usuario.setSexo(resultSet.getString("Sexo"));
                    usuario.setTelefono(resultSet.getString("Telefono"));
                    usuario.setCurp(resultSet.getString("Curp"));
                    usuario.setCelular(resultSet.getString("Celular"));
                    usuario.Rol = new Rol();

                    usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                    usuario.Rol.setNombre(resultSet.getString("NombreRol"));

                    resultuser.Object = usuario;

                }

                return true;

            });
        } catch (Exception ex) {
            resultuser.Correct = false;
            resultuser.ErrorMessage = ex.getLocalizedMessage();
            resultuser.ex = ex;
        }
        return resultuser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result AddAll(List<Usuario> usuarios) {
        Result result = new Result();
        try {
            jdbcTemplate.batchUpdate("{CALL AddUsuario(?,?,?,?,?,?,?,?,?,?,?,?)}", usuarios, usuarios.size(), (CallableStatement, usuario) -> {
                CallableStatement.setString(1, usuario.getUsername());
                CallableStatement.setString(2, usuario.getNombre());
                CallableStatement.setString(3, usuario.getApellidoPaterno());
                CallableStatement.setString(4, usuario.getApellidoMaterno());
                CallableStatement.setString(5, usuario.getEmail());
                CallableStatement.setString(6, usuario.getPassword());
                java.sql.Date sqlDate = new java.sql.Date(usuario.getFechaNacimiento().getTime());
                CallableStatement.setDate(7, sqlDate);
                CallableStatement.setString(8, usuario.getSexo());
                CallableStatement.setString(9, usuario.getTelefono());
                CallableStatement.setString(10, usuario.getCurp());
                CallableStatement.setInt(11, usuario.Rol.getIdRol());
                CallableStatement.setString(12, usuario.getCelular());

            });
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return result;
    }

    @Override
    public Result GetAllDinamico(Usuario usuario) {
        Result result = new Result();

        try {
            result.Correct = jdbcTemplate.execute("{CALL busquedausuariodireccionall(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setInt(4, usuario.Rol.getIdRol());
                callableStatement.registerOutParameter(5, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(5);

                result.Objects = new ArrayList<>();

                while (resultSet.next()) {
                    int IdUsuarioPorIngresar = resultSet.getInt("IdUsuario"); // truena aqui jaja
                    if (!result.Objects.isEmpty() && ((Usuario) result.Objects.get(result.Objects.size() - 1)).getIdUsuario() == IdUsuarioPorIngresar) {
                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                        Usuario usuarioBusqueda = ((Usuario) result.Objects.get(result.Objects.size() - 1));
                        usuarioBusqueda.Direcciones.add(direccion);
                    } else {
                        Usuario usuarioBusqueda = new Usuario();
                        usuarioBusqueda.setIdUsuario(IdUsuarioPorIngresar);
                        usuarioBusqueda.setUsername(resultSet.getString("Username"));
                        usuarioBusqueda.setNombre(resultSet.getString("NombreUsuario"));
                        usuarioBusqueda.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuarioBusqueda.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuarioBusqueda.setEmail(resultSet.getString("Email"));
                        usuarioBusqueda.setPassword(resultSet.getString("Password"));
                        usuarioBusqueda.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuarioBusqueda.setSexo(resultSet.getString("Sexo"));
                        usuarioBusqueda.setTelefono(resultSet.getString("Telefono"));
                        usuarioBusqueda.setCurp(resultSet.getString("Curp"));
                        usuarioBusqueda.setCelular(resultSet.getString("Celular"));
                        int IdDireccion = resultSet.getInt("IdDireccion");
                        if (IdDireccion != 0) {
                            usuarioBusqueda.Direcciones = new ArrayList<>();
                            Direccion direccion = new Direccion();
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            direccion.Colonia = new Colonia();
                            direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                            direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                            direccion.Colonia.Municipio = new Municipio();
                            direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                            direccion.Colonia.Municipio.Estado = new Estado();
                            direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                            direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                            direccion.Colonia.Municipio.Estado.Pais = new Pais();
                            direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                            direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                            usuarioBusqueda.Direcciones.add(direccion);
                        }
                        result.Objects.add(usuarioBusqueda);
                    }
                }
                return true;
            });

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

            return result;

    }
    
    

}
