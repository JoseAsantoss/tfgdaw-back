package edix.tfg.consumoCombustiblebk.restController;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import edix.tfg.consumoCombustiblebk.models.entity.Rol;
import edix.tfg.consumoCombustiblebk.services.IRolService;
import lombok.extern.log4j.Log4j2;


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
@Log4j2
public class RolController {

	@Autowired
	IRolService rolService;
	
	/**
	 * Metodo GET que devuelve la lista de tipos de usuario y un mensaje de estado.
	 * @return ResponseEntity<Map<String, Object>> con el response y el status del servidor.
	 */
	@Secured({
		"ROLE_PARTICULAR", 
		"ROLE_EMPRESA", 
		"ROLE_CONDUCTOR", 
		"ROLE_ADMIN"})
	@GetMapping({"", "/"})
	public ResponseEntity<List<Rol>> showRolUsuario() {
		
		List<Rol> lista = rolService.findAll();
		
		if ( lista.isEmpty() || lista == null ) {
			log.error("Error: No se encuentran registros en la base de datos");
		} else {
			log.info("Listado obtenido con éxito");
		}
	
		return new ResponseEntity<List<Rol>>(lista, HttpStatus.OK);		
	}
	
	/**
	 * Metodo POST que da de alta un nuevo tipo de usuario
	 * 
	 * @param tipoUsuario con el TipoUsuario a dar de alta
	 * @return ResponseEntity<Map<String, Object>> con el response y el status del servidor.
	 */
	@Secured({
		"ROLE_ADMIN"})
	@PostMapping("/nuevoRol")
	public ResponseEntity<Rol> createRole(@RequestBody Rol tipoUsuario) {
		
		Rol newTipo = new Rol();
		
		if (tipoUsuario != null) {
			
			try {
				newTipo = rolService.createTipoUsuario(tipoUsuario);
				log.info("El nuevo tipo de usuario "+ newTipo.getRolDescripcion() +" se ha creado con éxito");
			} catch(DataAccessException dae) {
				String message = dae.getMessage();
				message = message != null? message : "";
				log.error("error", message.concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			log.error("El nuevo tipo de usuario esta a nulo");
			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
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
	@Secured({
		"ROLE_ADMIN"})
	@PutMapping("/actualizaRol/{id}")
	public ResponseEntity<Rol> updateRol(
			@RequestBody Rol rolUsuario, 
			@PathVariable("id") Long idRol){
		
		Rol tipoAux = rolService.findById(idRol);
		rolUsuario.setRolId(idRol);
		
		System.out.println(tipoAux);
		if(tipoAux != null) {
			if (tipoAux.getRolDescripcion().compareTo(rolUsuario.getRolDescripcion()) == 0) {
				log.error("La descripción enviada es idéntica a la de la BBDD");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				
			} else {			
				try {
					tipoAux = rolService.updateRol(rolUsuario);
					log.info("Rol actualizado con éxito a " + tipoAux.getRolDescripcion());
				} catch (DataAccessException dae) {
					String message = dae.getMessage();
					message = message != null? message : "";
					log.error("error", "error: ".concat(message.concat(" - ").concat(dae.getLocalizedMessage()).concat(dae.getMostSpecificCause().getMessage())));
					log.error("Error al actualizar en la base de datos");
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			
		} else {
			log.error("El rol a actualizar no existe en la base de datos");
			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
		}
		
		return new ResponseEntity<Rol>(tipoAux, HttpStatus.OK);
	}
	
	/**
	 * Metodo Delete para eliminar un tipo de üsuario en la base de datos
	 * @param idUsaurioTipo de tipo Long
	 * @return ResponseEntity<Map<String, Object>> con el response y el status del servidor.
	 */
	@Secured({
		"ROLE_ADMIN"})
	@DeleteMapping("/eliminarRol/{id}")
	public ResponseEntity<?> deleteTipoUsuario(@PathVariable("id") Long idUsaurioTipo){
		
		try {
			rolService.deleteRol(idUsaurioTipo);
			log.info("Rol eliminado con éxito");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(DataAccessException dae) {
			log.error("Error al eliminar en la base de datos");
			String message = dae.getMessage();
			message = message != null? message : "";
			log.error(message.concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
