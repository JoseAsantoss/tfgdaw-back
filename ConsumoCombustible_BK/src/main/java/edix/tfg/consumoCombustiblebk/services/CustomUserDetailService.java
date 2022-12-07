package edix.tfg.consumoCombustiblebk.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edix.tfg.consumoCombustiblebk.dao.IUsuarioDao;
import edix.tfg.consumoCombustiblebk.models.entity.Usuario;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private IUsuarioDao usuarioDao; 
	
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = (Usuario)usuarioDao.findByUsuarioEmail(username);
		
		if (usuario == null) {
			log.info("Error en el login: no existe el usuario con email " + username + " en el sistema");
			throw new UsernameNotFoundException("Error en el login: no existe el usuario con email " + username + " en el sistema");
		}
		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRolDescripcion()))
				.peek(authority -> log.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		return new User(usuario.getUsuarioEmail(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

}
