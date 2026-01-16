package AVilchis.ProgramacionNCapasNoviembre25.Service;

import AVilchis.ProgramacionNCapasNoviembre25.DAO.IUsuarioJPARepository;
import AVilchis.ProgramacionNCapasNoviembre25.JPA.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsJPAService implements UserDetailsService{
    
    private IUsuarioJPARepository iUsuarioJPARepository;
    
    public UserDetailsJPAService(IUsuarioJPARepository iUsuarioJPARepository){
        this.iUsuarioJPARepository = iUsuarioJPARepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Usuario usuario = iUsuarioJPARepository.findByUsername(username);
        
        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.Rol.getNombre())
                .build();
    }
    
}
