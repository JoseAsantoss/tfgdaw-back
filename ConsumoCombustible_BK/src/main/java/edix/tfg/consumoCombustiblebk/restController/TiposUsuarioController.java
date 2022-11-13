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

import edix.tfg.consumoCombustiblebk.models.entity.Rol;
import edix.tfg.consumoCombustiblebk.services.ITipoUsuarioService;


/**
 * Clase RestController con los endpoints del CRUD para tipos de usuarios
 * 
 * @autor Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 25/10/2022
 *
 */
@RestController
@RequestMapping("/api/tiposUsuario")
public class TiposUsuarioController {

	@Autowired
	ITipoUsuarioService tipoUsuarioService;
	
	/**
	 * Metodo GET que devuelve la lista de tipos de usuario y un mensaje de estado.
	 * @return ResponseEntity<Map<String, Object>> con el response y el status del servidor.
	 */
	@GetMapping({"", "/"})
	public ResponseEntity<?> showTipoUsuario() {
		
		List<Rol> lista = tipoUsuarioService.findAll();
		Map<String, Object> response = new HashMap<String, Object>();
		
		if(lista.isEmpty() || lista == null) {
			response.put("mensaje", "Error: No se encuentran registros en la base de datos");
		}else {
			response.put("mensaje", "Listado obtenido con éxito");
			response.put("lista", lista);
		}
	
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}
	
	/**
	 * Metodo POST que da de alta un nuevo tipo de usuario
	 * 
	 * @param tipoUsuario con el TipoUsuario a dar de alta
	 * @return ResponseEntity<Map<String, Object>> con el response y el status del servidor.
	 */
	@PostMapping("/nuevoTipo")
	public ResponseEntity<?> createTipoUsuario(@RequestBody Rol tipoUsuario) {
		Rol newTipo = new Rol();
		Map<String, Object> response = new HashMap<String, Object>();
		
		if (tipoUsuario != null) {
			
			try {
				newTipo = tipoUsuarioService.createTipoUsuario(tipoUsuario);
				response.put("mensaje", "El nuevo tipo de usuario se ha creado con éxito");
				response.put("tipoUsuario", newTipo);
			}catch(DataAccessException dae) {
				response.put("mensaje", "Error al realizar el insert en la base de datos");
				response.put("error", dae.getMessage().concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			response.put("mensaje", "El nuevo tipo de usuario esta a nulo");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.I_AM_A_TEAPOT);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo POST que da de alta un nuevo tipo de usuario
	 * 
	 * @param tipoUsuario con el TipoUsuario con los nuevos datos
	 * @param idTipoUsuario con el id del TipoUsuario a modificar
	 * @return ResponseEntity<Map<String, Object>> con el response y el status del servidor.
	 */
	@PutMapping("/actualizaTipo/{id}")
	public ResponseEntity<?> updateTipoUsuario(@RequestBody Rol tipoUsuario, @PathVariable("id") Long idTipoUsuario){
		
		Rol tipoAux = tipoUsuarioService.findById(idTipoUsuario);
		Map<String, Object> response = new HashMap<String, Object>();
		
		if(tipoAux != null) {
			
			tipoAux.setTipoUsuarioNombre(tipoUsuario.getTipoUsuarioNombre());
			
			try {
				tipoAux = tipoUsuarioService.updateTipoUsuario(tipoUsuario);
				response.put("mensaje", "Tipo actualizado con éxito");
				response.put("tipoUsuario", tipoAux);
			}catch(DataAccessException dae) {
				response.put("mensaje", "Error al actualizar en la base de datos");
				response.put("error", dae.getMessage().concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}else {
			response.put("mensaje", "El nuevo tipo de usuario esta a nulo");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.I_AM_A_TEAPOT);
		}
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	/**
	 * Metodo Delete para eliminar un tipo de üsuario en la base de datos
	 * @param idUsaurioTipo de tipo Long
	 * @return ResponseEntity<Map<String, Object>> con el response y el status del servidor.
	 */
	@DeleteMapping("/eliminarTipo/{id}")
	public ResponseEntity<?> deleteTipoUsuario(@PathVariable("id") Long idUsaurioTipo){
		
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			tipoUsuarioService.deleteTipoUsuario(idUsaurioTipo);
			response.put("mensaje", "Tipo de usuario eliminado con éxito");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		}catch(DataAccessException dae) {
			response.put("mensaje", "Error al eliminar en la base de datos");
			response.put("error", dae.getMessage().concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
