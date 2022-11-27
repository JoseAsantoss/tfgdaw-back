package edix.tfg.consumoCombustiblebk.restController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edix.tfg.consumoCombustiblebk.constants.ApplicationConstants;
import edix.tfg.consumoCombustiblebk.models.entity.Rol;
import edix.tfg.consumoCombustiblebk.models.entity.Usuario;
import edix.tfg.consumoCombustiblebk.services.IRolService;
import edix.tfg.consumoCombustiblebk.services.IUsuarioService;
import lombok.extern.log4j.Log4j2;


@CrossOrigin(origins = ApplicationConstants.ORIGINS)
@RestController
@RequestMapping("/api")
@Log4j2
public class UsuarioController {

	@Autowired
	IUsuarioService iUsuarioService;
	
	@Autowired
	IRolService iRolService;
	
	/**
	 * End point para listar todos los usuarios de la aplicacion
	 * 
	 * Mediante parámetros en la cabecera se pueden realizar filtrado 
	 * de los usuarios, con parámetro de búsqueda, y 
	 * además parámetros varios para elegir buscar por cada uno de 
	 * los atributos del usuario (nombre, apellido1, apellido2, email)
	 * 
	 * @return List<Usuario> con los usuarios.
	 */
	@Secured({"ROLE_PARTICULAR", "ROLE_EMPRESA", "ROLE_ADMIN"})
	@GetMapping({"/usuarios", "/usuarios/"})
	public ResponseEntity<?> listaUsuarios(
			@RequestParam Map<String, String> params) 
					 throws ParseException {
		
		log.info("Petición de lista de usuarios");
		
		Map<String, Object> resp = new HashMap<String, Object>();
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();		
		Set<Usuario> listaCombinada = new LinkedHashSet<Usuario>();
		
		if (params.size() == 0) {
			try {
				log.info("Se obtiene la lista de usuarios de la base de datos");
				listaUsuarios = iUsuarioService.showUsuarios();
			} catch (NullPointerException npe) {
				log.error(npe.getStackTrace());
				log.error(npe.getCause());
				log.error(npe.initCause(npe));
				resp.put("error", "Por favor inténtelo pasados unos minutos");
				return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		if (params.size() > 5) { //Aceptar máx 5 parámetros
		      throw new ResponseStatusException(
		    		  HttpStatus.BAD_REQUEST, 
		    		  "Sólo se pueden incluir 5 parámetros máximo");
		}		
		
		if (params.containsKey("buscar")) {
			String busqueda = params.get("buscar");
			
			if (params.containsKey("email")) {
				listaCombinada.addAll(iUsuarioService.searchUsuarioEmail(busqueda));
			}			
			if (params.containsKey("nombre")) {
				listaCombinada.addAll(iUsuarioService.searchUsuarioNombre(busqueda));
			}			
			if (params.containsKey("apellido1")) {
				listaCombinada.addAll(iUsuarioService.searchUsuarioApellido1(busqueda));
			}			
			if (params.containsKey("apellido2")) {
				listaCombinada.addAll(iUsuarioService.searchUsuarioApellido2(busqueda));
			}
			
			listaUsuarios = new ArrayList<Usuario>(listaCombinada);		
		}
		
		if (listaUsuarios.isEmpty() || listaUsuarios.size() == 0) {
			log.info("No se ha obtenido datos de la base de datos");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			log.info("Lista populada correctamente.");
			resp.put("lista", listaUsuarios);
			log.info("Se envia response y estatus");
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}

	}
	
	
	/**
	 * End point para mostrar un usuario por su id
	 * @param usuarioId de tipo Long
	 * @return Usuario buscado
	 */
	@Secured({"ROLE_PARTICULAR", "ROLE_EMPRESA", "ROLE_ADMIN"})
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<?> UsuarioPorId(@PathVariable Long usuarioId) {
		log.info("Petición de usuarios por su Id");
		Map<String, Object> resp = new HashMap<String, Object>();
		Usuario usuario = null;
		
		try {
			log.info("Se recupera el usuario de la base de datos");
			usuario = iUsuarioService.showUsuarioById(usuarioId);
			resp.put("usuario", usuario);
		}catch (NullPointerException npe) {
			log.error(npe.getStackTrace());
			log.error(npe.getCause());
			log.error(npe.initCause(npe));
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se envia response y estatus");
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
	}
	
	/**
	 * Metodo para dar de alta un nuevo usuario
	 * @param newUsuario de tipo Usuario
	 * @return 
	 */
	@Secured({"ROLE_PARTICULAR", "ROLE_EMPRESA", "ROLE_ADMIN"})
	@PostMapping("/usuario/nuevo_usuario")
	public ResponseEntity<?> altaUsuario(@RequestBody Usuario newUsuario) {
		log.info("Se da de alta un nuevo usuario");
		
		Usuario user = new Usuario();
		List<Rol> rolesUsuario = new ArrayList<Rol>();
		List<Rol> rolesUsuarioFinal = new ArrayList<Rol>();
		
		Map<String, Object> resp = new HashMap<String, Object>();
		
		try {
			rolesUsuario = newUsuario.getRoles();
			for (Rol rol : rolesUsuario) {
				rol = iRolService.findByRolName(rol.getRolDescripcion());
				rolesUsuarioFinal.add(rol);
			}
			
			newUsuario.setRoles(rolesUsuarioFinal);
			System.out.println(newUsuario);
			
			
			log.info("Se manda a base de datos el nuevo usuario");
			newUsuario.setEnabled(true);
			newUsuario.setUsername(newUsuario.getUsuarioEmail());
			System.out.println(newUsuario);
			user = iUsuarioService.createUsuario(newUsuario);
			log.info("Usuario creado con éxito");
			resp.put("mensaje", "Usuario dado de alta con éxito");
			resp.put("usuario", user);
		}catch(DataAccessException dae) {
			log.error("Error al realizar el insert en la base de datos");
			log.error(dae.getMessage().concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se envia response y estatus");
		//return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo para modificar un usuario
	 * @param updateUsuario de tipo Usuario
	 * @param idUsuario de tipo Long
	 * @return Usuario Modificado
	 */
	@Secured({"ROLE_PARTICULAR", "ROLE_EMPRESA", "ROLE_ADMIN"})
	@PutMapping("/usuario/modifica-usuario/{idUsuario}")
	public ResponseEntity<?> modificaUsuario(@RequestBody Usuario newUsuario, @PathVariable Long idUsuario) {
		log.info("Se quiere modificar un usuario");
		
		log.info("Se recupera el usuario de la base de datos");
		Usuario usuActual = iUsuarioService.showUsuarioById(idUsuario);
		

		System.out.println(usuActual);
	
		Map<String, Object> resp = new HashMap<String, Object>();
		
		if (usuActual != null) {
			
			if (newUsuario.getUsuarioNombre() != null) {
				log.info("Se actualiza el nombre");
				usuActual.setUsuarioNombre(newUsuario.getUsuarioNombre());
			}
			
			if (newUsuario.getUsuarioApellido1() != null) {
				log.info("Se actualiza apellido1");
				usuActual.setUsuarioApellido1(newUsuario.getUsuarioApellido1());
			}
			
			if (newUsuario.getUsuarioApellido2() != null) {
				log.info("Se actualiza apellido2");
				usuActual.setUsuarioApellido2(newUsuario.getUsuarioApellido2());
			}
			
			if (newUsuario.getUsuarioEmail() != null) {
				log.info("Se actualiza Email");
				usuActual.setUsuarioEmail(newUsuario.getUsuarioEmail());
			}
			
			if (newUsuario.getPassword() != null) {
				log.info("Se actualiza password");
				usuActual.setPassword(newUsuario.getPassword());
			}
			
			if (newUsuario.getRoles() != null) {
				log.info("Se actualizan los roles");
				List<Rol> rolesUsuario = new ArrayList<Rol>();
				List<Rol> rolesUsuarioFinal = new ArrayList<Rol>();
				rolesUsuario = newUsuario.getRoles();
				for (Rol rol : rolesUsuario) {
					rol = iRolService.findByRolName(rol.getRolDescripcion());
					rolesUsuarioFinal.add(rol);
				}
				newUsuario.setRoles(rolesUsuarioFinal);
				
				usuActual.setRoles(newUsuario.getRoles());
			}
			
			System.out.println(usuActual);
			
			try {
				log.info("Se actualiza el usuario en la base de datos");
				iUsuarioService.updateUsuario(usuActual);
				log.info("Usuario modificado con éxito");
				resp.put("mensaje","Usuario actualizado correctamente");
				resp.put("usuario", usuActual);
			}catch(DataAccessException dae) {
				log.error("message", "Error al modificar en la base de datos");
				log.error("error", dae.getMessage().concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_MODIFIED);
			}
			
		} else {
			resp.put("mensaje", "Usuario no encontrado");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<Usuario>(usuActual, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo para eliminar un usuario
	 * @param idUsuario de tipo Long
	 * @return Usuario Modificado
	 */
	@Secured({"ROLE_PARTICULAR", "ROLE_EMPRESA", "ROLE_ADMIN"})
	@DeleteMapping("/usuario/elimina_usuario/{idUsuario}")
	public ResponseEntity<?> eliminaUsuario(@PathVariable Long idUsuario) {
		log.info("Se elimina a un usuario");
		Map<String, Object> resp = new HashMap<String, Object>();
	
		try {
			log.info("Se manda eliminar el usuario en la base de datos");
			iUsuarioService.deleteUsuario(idUsuario);
			log.info("Usuario eliminado de la base de datos");
			resp.put("mensaje", "Usuario eliminado con éxito");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
		}catch(DataAccessException dae) {
			log.error("mensaje", "Error al eliminar de la base de datos");
			log.error(dae.getMessage().concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
			resp.put("mensaje", "Se ha producido un error. Inténtelo en unos minutos");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
}
