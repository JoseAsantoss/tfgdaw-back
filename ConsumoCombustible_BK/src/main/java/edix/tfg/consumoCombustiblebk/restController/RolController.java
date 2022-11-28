package edix.tfg.consumoCombustiblebk.restController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edix.tfg.consumoCombustiblebk.models.entity.Rol;
import edix.tfg.consumoCombustiblebk.services.IRolService;


/**
 * Clase RestController con los endpoints del CRUD para tipos de usuarios
 * 
 * @autor Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 25/10/2022
 *
 */
@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/roles")
public class RolController {

	@Autowired
	IRolService rolService;
	
	/**
	 * Metodo GET que devuelve la lista de tipos de usuario y un mensaje de estado.
	 * @return ResponseEntity<Map<String, Object>> con el response y el status del servidor.
	 */
	@GetMapping({"", "/"})
	public ResponseEntity<?> showRolUsuario() {
		
		List<Rol> lista = rolService.findAll();
		Map<String, Object> response = new HashMap<String, Object>();
		
		if(lista.isEmpty() || lista == null) {
			response.put("mensaje", "Error: No se encuentran registros en la base de datos");
		}else {
			response.put("mensaje", "Listado obtenido con éxito");
			response.put("lista", lista);
		}
	
		return new ResponseEntity<List<Rol>>(lista, HttpStatus.OK);
		
	}
	
	/**
	 * Metodo POST que da de alta un nuevo tipo de usuario
	 * 
	 * @param tipoUsuario con el TipoUsuario a dar de alta
	 * @return ResponseEntity<Map<String, Object>> con el response y el status del servidor.
	 */
	@PostMapping("/nuevoRol")
	public ResponseEntity<?> createRole(@RequestBody Rol tipoUsuario) {
		Rol newTipo = new Rol();
		Map<String, Object> response = new HashMap<String, Object>();
		
		if (tipoUsuario != null) {
			
			try {
					newTipo = rolService.createTipoUsuario(tipoUsuario);
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
		
		return new ResponseEntity<Rol>(newTipo, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo POST que da de alta un nuevo tipo de usuario
	 * 
	 * @param rolUsuario con el TipoUsuario con los nuevos datos
	 * @param idTipoUsuario con el id del TipoUsuario a modificar
	 * @return ResponseEntity<Map<String, Object>> con el response y el status del servidor.
	 */
	@PutMapping("/actualizaRol/{id}")
	public ResponseEntity<?> updateRol(
			@RequestBody Rol rolUsuario, 
			@PathVariable("id") Long idRol){
		
		Rol tipoAux = rolService.findById(idRol);
		rolUsuario.setRolId(idRol);
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		System.out.println(tipoAux);
		if(tipoAux != null) {
			if (tipoAux.getRolDescripcion().compareTo(rolUsuario.getRolDescripcion()) == 0) {
				response.put("mensaje", "La descripción enviada es idéntica a la de la BBDD");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				
			} else {			
				try {
					tipoAux = rolService.updateRol(rolUsuario);
					response.put("mensaje", "Rol actualizado con éxito");
					response.put("tipoUsuario", tipoAux);
				}catch(DataAccessException dae) {
					response.put("mensaje", "Error al actualizar en la base de datos");
					response.put("error", dae.getMessage().concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			
		} else {
			response.put("mensaje", "El rol a actualizar no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.I_AM_A_TEAPOT);
		}
		
		//return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		return new ResponseEntity<Rol>(tipoAux, HttpStatus.OK);
	}
	
	/**
	 * Metodo Delete para eliminar un tipo de üsuario en la base de datos
	 * @param idUsaurioTipo de tipo Long
	 * @return ResponseEntity<Map<String, Object>> con el response y el status del servidor.
	 */
	@DeleteMapping("/eliminarRol/{id}")
	public ResponseEntity<?> deleteTipoUsuario(@PathVariable("id") Long idUsaurioTipo){
		
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			rolService.deleteRol(idUsaurioTipo);
			response.put("mensaje", "Rol eliminado con éxito");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		}catch(DataAccessException dae) {
			response.put("mensaje", "Error al eliminar en la base de datos");
			response.put("error", dae.getMessage().concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
