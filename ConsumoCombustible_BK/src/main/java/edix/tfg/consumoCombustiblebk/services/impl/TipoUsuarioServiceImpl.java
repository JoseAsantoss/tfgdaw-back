package edix.tfg.consumoCombustiblebk.services.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edix.tfg.consumoCombustiblebk.dao.ITipoUsuarioDao;
import edix.tfg.consumoCombustiblebk.models.entity.Rol;
import edix.tfg.consumoCombustiblebk.services.ITipoUsuarioService;

/**
 * Clase Servicio que implementa los metodos del CRUD para 
 * los tipos de usuarios que maneja la aplicacion.
 * 
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 25/10/2022
 *
 */
@Service
public class TipoUsuarioServiceImpl implements ITipoUsuarioService {

	@Autowired
	private ITipoUsuarioDao iTipoUsuarioDao;
	
	/**
	 * Metodo que devuelve la lista de tipos de usuarios de la aplicacion
	 * @return List<TipoUsuario> - Lista de tipos de usuarios
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Rol> findAll() {
		return (List<Rol>)iTipoUsuarioDao.findAll();
	}
	
	/**
	 * Metodo que devuelve un objeto de tipo TipoUsuario de la base de datos
	 * @param idTipo de tipo Long - id del Tipo de usuario buscado.
	 * @return {@link Rol} - Tipo usuario buscado.
	 */
	@Override
	@Transactional(readOnly = true)
	public Rol findById(Long idTipo) {
		return iTipoUsuarioDao.findById(idTipo).orElse(null);
	}

	/**
	 * Metodo que crea un nuevo Tipo de usuarios
	 * @param tipoUsuario de tipo {@link Rol}
	 * @return {@link Rol} - Tipo usuario creado
	 */
	@Override
	public Rol createTipoUsuario(Rol tipoUsuario) {
		return iTipoUsuarioDao.saveAndFlush(tipoUsuario);
	}

	/**
	 * Metodo para actualizar un tipo de usuario
	 * @param tipoUsuario de tipo {@link Rol}
	 * @return {@link Rol} - Tipo usuario actualizado
	 * 
	 */
	@Override
	public Rol updateTipoUsuario(Rol tipoUsuario) {	
		return iTipoUsuarioDao.saveAndFlush(tipoUsuario);
	}

	/**
	 * Metodo para eliminar un registro de tipoUsuaripo.<br>
	 * No devuelve resultado.
	 * @param idTipo de tipo Long con el id a eliminar
	 * 
	 */
	@Override
	public void deleteTipoUsuario(Long idTipo) {
		iTipoUsuarioDao.deleteById(idTipo);
		
	}

}
