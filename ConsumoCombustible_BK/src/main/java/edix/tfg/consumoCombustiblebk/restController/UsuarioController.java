package edix.tfg.consumoCombustiblebk.restController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edix.tfg.consumoCombustiblebk.models.entity.Usuario;
import edix.tfg.consumoCombustiblebk.services.IUsuarioService;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@Log4j2
public class UsuarioController {

	@Autowired
	IUsuarioService iUsuarioService;
	
	/**
	 * End point para listar todos los usuarios de la aplicacion
	 * @return List<Usuario> con los usuarios.
	 */
	@GetMapping({"/usuarios", "/usuarios/"})
	public ResponseEntity<?> listaUsuarios() {
		
		log.info("Petición de lista de usuarios");
		
		Map<String, Object> resp = new HashMap<String, Object>();
		
		log.info("Se obtiene la lista de ususarios de la base de datos");
		List<Usuario> lista = iUsuarioService.showUsuarios();
		
		if (lista.isEmpty() || lista.size() == 0) {
			log.info("No se ha obtenido datos de la base de datos");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			log.info("Lista populada correctamente.");
			resp.put("lista", lista);
			log.info("Se envia response y estatus");
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}

	}
	
	
	/**
	 * End point para mostrar un usuario por su id
	 * @param usuarioId de tipo Long
	 * @return Usuario buscado
	 */
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
	@PostMapping("/usuario/nuevo_usuario")
	public ResponseEntity<?> altaUsuario(@RequestBody Usuario newUsuario) {
		log.info("Se da de alta un nuevo usuario");
		
		Usuario user = new Usuario();
		Map<String, Object> resp = new HashMap<String, Object>();
		
		try {
			log.info("Se manda a base de datos el nuevo usuario");
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
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo para modificar un usuario
	 * @param updateUsuario de tipo Usuario
	 * @param idUsuario de tipo Long
	 * @return Usuario Modificado
	 */
	@PutMapping("/usuario/modifica-usuario/{idUsuario}")
	public ResponseEntity<?> modificaUsuario(@RequestBody Usuario newUsuario, @PathVariable Long idUsuario) {
		log.info("Se quiere modificar un usuario");
		
		log.info("Se recupera el usuario de la base de datos");
		Usuario usuActual = iUsuarioService.showUsuarioById(idUsuario);
	
		Map<String, Object> resp = new HashMap<String, Object>();
		
		if (usuActual != null) {
			log.info("Se actualiza el nombre");
			usuActual.setUsuarioNombre(newUsuario.getUsuarioNombre());
			log.info("Se actualiza apellido1");
			usuActual.setUsuarioApellido1(newUsuario.getUsuarioApellido1());
			log.info("Se actualiza apellido2");
			usuActual.setUsuarioApellido2(newUsuario.getUsuarioApellido2());
			log.info("Se actualiza Email");
			usuActual.setUsuarioEmail(newUsuario.getUsuarioEmail());
			log.info("Se actualiza password");
			usuActual.setUsuarioPassword(newUsuario.getUsuarioPassword());
			
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
		
		
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
	}
	
	/**
	 * Metodo para eliminar un usuario
	 * @param idUsuario de tipo Long
	 * @return Usuario Modificado
	 */
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
