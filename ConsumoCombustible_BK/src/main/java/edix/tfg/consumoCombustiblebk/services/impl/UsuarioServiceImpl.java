package edix.tfg.consumoCombustiblebk.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edix.tfg.consumoCombustiblebk.dao.IUsuarioDao;
import edix.tfg.consumoCombustiblebk.models.entity.Usuario;
import edix.tfg.consumoCombustiblebk.services.IUsuarioService;

/**
 * Clase que implementa los metodos de la interface UsuarioService
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 27/10/2022
 *
 */
@Service
public class UsuarioServiceImpl implements IUsuarioService{

	@Autowired
	private IUsuarioDao iUsuarioDao;
	
	/**
	 * Metodo para mostrar todos los usuarios de la aplicacion
	 * @return List con los usuarios
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Usuario> showUsuarios() {
		return (List<Usuario>)iUsuarioDao.findAll();
	}

	/**
	 * Metodo que muestra un usuario buscado por su ide
	 * @param idUsuario de tipo Long
	 * @return Usuario buscado
	 */
	@Transactional(readOnly = true)
	@Override
	public Usuario showUsuarioById(Long idUsuario) {
		return (Usuario)iUsuarioDao.findById(idUsuario).orElse(null);
	}

	/**
	 * Metodo para dar de alta un nuevo usuario en la aplicacion
	 * @param newUsuario de tipo Usuario
	 * @return Usuario creado
	 */
	@Override
	public Usuario createUsuario(Usuario newUsuario) {
		return iUsuarioDao.save(newUsuario);
	}

	/**
	 * Metodo para actualizar un usuario de la aplicacion
	 * @param updateUsuario usuario a actualizar
	 * @return Usuario actualizado
	 */
	@Override
	public Usuario updateUsuario(Usuario updateUsuario) {
		return iUsuarioDao.save(updateUsuario);
	}

	/**
	 * Metodo para eliminar un usuario
	 * @param idUsuario de tipo Long
	 */
	@Override
	public void deleteUsuario(Long idUsuario) {
		iUsuarioDao.deleteById(idUsuario);	
	}

}
