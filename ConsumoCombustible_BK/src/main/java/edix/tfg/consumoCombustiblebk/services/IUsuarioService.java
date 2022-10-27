package edix.tfg.consumoCombustiblebk.services;

import java.util.List;

import edix.tfg.consumoCombustiblebk.models.entity.Usuario;

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
	public Usuario createUsuario(Usuario newUsuario);
	public Usuario updateUsuario(Usuario updateUsuario);
	public void deleteUsuario(Long idUsuario);
}
