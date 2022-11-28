package edix.tfg.consumoCombustiblebk.services.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edix.tfg.consumoCombustiblebk.dao.IRolDao;
import edix.tfg.consumoCombustiblebk.dao.IUsuarioDao;
import edix.tfg.consumoCombustiblebk.models.entity.Rol;
import edix.tfg.consumoCombustiblebk.models.entity.Usuario;
import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;
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
	
	@Autowired
	private IRolDao iRolUsuarioDao;
	
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
	 * Metodo para buscar usuarios
	 * @param textoBusqueda de tipo String
	 * @param 4xBoolean para indicar si busca en email, 
	 * nombre, o en cada uno de los apellidos.
	 * @return Usuario buscado
	 */
	
	//https://www.digitalocean.com/community/tutorials/set-to-list-in-java
	@Transactional(readOnly = true)
	@Override
	public List<Usuario> searchUsuario(
			String textoBusqueda,
			Boolean email, 
			Boolean nombre,
			Boolean apellido1,
			Boolean apellido2) {
		
		Set<Usuario> listaCombinada = new LinkedHashSet<Usuario>();
		
		if (email) {
			listaCombinada.addAll(iUsuarioDao.busquedaUsuarioEmail(textoBusqueda));
		}
		
		if (nombre) {
			listaCombinada.addAll(iUsuarioDao.busquedaUsuarioNombre(textoBusqueda));
		}
		
		if (apellido1) {
			listaCombinada.addAll(iUsuarioDao.busquedaUsuarioApellido1(textoBusqueda));
		}
		
		if (apellido2) {
			listaCombinada.addAll(iUsuarioDao.busquedaUsuarioApellido2(textoBusqueda));
		}
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>(listaCombinada);
		
		return listaUsuarios;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Usuario> searchUsuarioEmail(
			String textoBusqueda) {
		return iUsuarioDao.busquedaUsuarioEmail(textoBusqueda);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Usuario> searchUsuarioNombre(
			String textoBusqueda) {
		return iUsuarioDao.busquedaUsuarioNombre(textoBusqueda);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Usuario> searchUsuarioApellido1(
			String textoBusqueda) {
		return iUsuarioDao.busquedaUsuarioApellido1(textoBusqueda);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Usuario> searchUsuarioApellido2(
			String textoBusqueda) {
		return iUsuarioDao.busquedaUsuarioApellido2(textoBusqueda);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Usuario> searchUsuarioEmpresa(
			String empresaCif) {
		return iUsuarioDao.listarUsuariosEmpresa(empresaCif);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Usuario> searchConductoresEmpresa(
			String empresaCif, String rolDescripcion) {
		return iUsuarioDao.listarConductoresEmpresa(empresaCif, rolDescripcion);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Integer countConductoresEmpresa(
			String empresaCif, String rolDescripcion) {
		return iUsuarioDao.contarConductoresEmpresa(empresaCif, rolDescripcion);
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

	@Override
	public List<Vehiculo> searchVehiculosUsuario(Long idUsuario) {
		Usuario usuario = iUsuarioDao.getById(idUsuario);
		return usuario.getVehiculos();
	}

	public List<Rol> addRolUsuario(List<Rol> roles, Long rol) {
		
		if (roles != null) {
			if(roles.contains(iRolUsuarioDao.getById(rol))) {
				return null;
			}
		}
		
		roles = new ArrayList<Rol>();
		
		int size = roles.size();
		if(size != 0) size +=1;
		
		roles.add(size, iRolUsuarioDao.getById(rol));
		return roles;
	}

	@Override
	public Usuario findByUsername(String email) {
		Usuario u = null;
		
		if(email != null || email != "") {
			u = iUsuarioDao.findByUsuarioEmail(email);
		}
		return u;
	}
}
