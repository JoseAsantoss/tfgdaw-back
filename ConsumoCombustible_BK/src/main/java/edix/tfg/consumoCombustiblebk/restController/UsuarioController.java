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

@RestController
@RequestMapping("/api")
public class UsuarioController {

	@Autowired
	IUsuarioService iUsuarioService;
	
	/**
	 * End point para listar todos los usuarios de la aplicacion
	 * @return List<Usuario> con los usuarios.
	 */
	@GetMapping({"/usuarios", "/usuarios/"})
	public List<Usuario> listaUsuarios() {
		return iUsuarioService.showUsuarios();
	}
	
	
	/**
	 * End point para mostrar un usuario por su id
	 * @param usuarioId de tipo Long
	 * @return Usuario buscado
	 */
	@GetMapping("/usuario/{usuarioId}")
	public Usuario UsuarioPorId(@PathVariable Long usuarioId) {
		return iUsuarioService.showUsuarioById(usuarioId);
	}
	
	/**
	 * Metodo para dar de alta un nuevo usuario
	 * @param newUsuario de tipo Usuario
	 * @return 
	 */
	@PostMapping("/usuario/nuevo_usuario")
	public ResponseEntity<?> altaUsuario(@RequestBody Usuario newUsuario) {
		
		Usuario user = new Usuario();
		Map<String, Object> resp = new HashMap<String, Object>();
		
		try {
			user = iUsuarioService.createUsuario(newUsuario);
			resp.put("mensaje", "Usuario creado con éxito");
			resp.put("usuario", user);
		}catch(DataAccessException dae) {
			resp.put("mensaje", "Error al realizar el insert en la base de datos");
			resp.put("error", dae.getMessage().concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
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
		
		Usuario usuActual = iUsuarioService.showUsuarioById(idUsuario);
	
		Map<String, Object> resp = new HashMap<String, Object>();
		
		if (usuActual != null) {
			usuActual.setUsuarioId(newUsuario.getUsuarioId());
			usuActual.setUsuarioNombre(newUsuario.getUsuarioNombre());
			usuActual.setUsuarioApellido1(newUsuario.getUsuarioApellido1());
			usuActual.setUsuarioApellido2(newUsuario.getUsuarioApellido2());
			usuActual.setUsuarioEmail(newUsuario.getUsuarioEmail());
			usuActual.setUsuarioPassword(newUsuario.getUsuarioPassword());
			usuActual.setTipoUsuario(newUsuario.getTipoUsuario());
			
			try {
				iUsuarioService.updateUsuario(usuActual);
				resp.put("mensaje", "Usuario modificado con éxito");
				resp.put("usuario", usuActual);
			}catch(DataAccessException dae) {
				resp.put("message", "Error al modificar en la base de datos");
				resp.put("error", dae.getMessage().concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
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
		
		Map<String, Object> resp = new HashMap<String, Object>();
	
		try {
			iUsuarioService.deleteUsuario(idUsuario);
			resp.put("mensaje", "Usuario eliminado con éxito");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
		}catch(DataAccessException dae) {
			resp.put("mensaje", "Error al eliminar de la base de datos");
			resp.put("error", dae.getMessage().concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
}
