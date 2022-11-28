package edix.tfg.consumoCombustiblebk.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edix.tfg.consumoCombustiblebk.models.entity.Rol;
import edix.tfg.consumoCombustiblebk.models.entity.Usuario;
import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;

/**
 * Interfaz UsuarioService
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 27/10/2022
 *
 */
public interface IUsuarioService {

	public List<Usuario> showUsuarios();
	public Usuario showUsuarioById(Long idUsuario);
	public List<Usuario> searchUsuarioEmail(String textoBusqueda);
	public List<Usuario> searchUsuarioNombre(String textoBusqueda);
	public List<Usuario> searchUsuarioApellido1(String textoBusqueda);
	public List<Usuario> searchUsuarioApellido2(String textoBusqueda);	
	public List<Vehiculo> searchVehiculosUsuario(Long idUsuario);	
	public Usuario createUsuario(Usuario newUsuario);
	public Usuario updateUsuario(Usuario updateUsuario);
	public void deleteUsuario(Long idUsuario);
	List<Rol> addRolUsuario(List<Rol> roles, Long rol);
	Usuario findByUsername(String email);
	/**
	 * Metodo para buscar usuarios
	 * @param textoBusqueda de tipo String
	 * @param 4xBoolean para indicar si busca en email, 
	 * nombre, o en cada uno de los apellidos.
	 * @return Usuario buscado
	 */
	List<Usuario> searchUsuario(String textoBusqueda, Boolean email, Boolean nombre, Boolean apellido1,
			Boolean apellido2);
	List<Usuario> searchUsuarioEmpresa(String empresaCif);
}
