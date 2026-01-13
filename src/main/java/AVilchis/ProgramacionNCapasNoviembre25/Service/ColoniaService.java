/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AVilchis.ProgramacionNCapasNoviembre25.Service;

import AVilchis.ProgramacionNCapasNoviembre25.DAO.IColoniaJPARepository;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Colonia;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Result;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColoniaService {
    @Autowired
    IColoniaJPARepository coloniaJpaRepository;
    
    public Result getByMuncipio(int idMunicipio){
        Result result = new Result();
        try {
            result.Objects = new ArrayList();
            for(Colonia colonia: coloniaJpaRepository.findAll()){
                if(colonia.Municipio.getIdMunicipio() == idMunicipio){
                    result.Objects.add(colonia);
                }
            
            }
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ex=ex;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return result;
    } 
}
