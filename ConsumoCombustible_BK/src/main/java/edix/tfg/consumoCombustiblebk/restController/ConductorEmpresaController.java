package edix.tfg.consumoCombustiblebk.restController;

import java.util.ArrayList;
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

import edix.tfg.consumoCombustiblebk.models.entity.ConductorEmpresa;
import edix.tfg.consumoCombustiblebk.models.entity.Usuario;
import edix.tfg.consumoCombustiblebk.services.IConductorEmpresaService;
import edix.tfg.consumoCombustiblebk.services.IUsuarioService;
import lombok.extern.log4j.Log4j2;

/**
 * Controlador para el CRUD de la clase ConductorEmpresa.<br>
 * Se fijan los endpoints que por lo que respondera el API
 * 
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 03/11/2022
 *
 */
@RestController
@RequestMapping("/api")
@Log4j2
public class ConductorEmpresaController {
	
	@Autowired
	IConductorEmpresaService iConductorEmpService;
	
	@Autowired
	IUsuarioService iUsuarioServices;

	/**
	 * Metodo que lista todos los usuarios con el rol conductor
	 * @return ResponseEntity<Map<String, Object>>
	 */
	@GetMapping("/usuarios/conductores")
	public ResponseEntity<?> mostrarTodosLosConductores() {
		log.info("Se pide listar todos los conductores");
		log.info("Se crea el objeto response de tipo Map");
		Map<String, Object> response = new HashMap<String, Object>();
		
		log.info("Se crea el objeto lista vacio");
		List<ConductorEmpresa> lista = new ArrayList<ConductorEmpresa>();
		
		try {
			log.info("Se popula la lista con los datos de la bbdd");
			lista = iConductorEmpService.mostrarTodos();
			log.info("Lista populada");
		} catch (NullPointerException npe) {
			log.error(npe.getStackTrace());
			log.error(npe.getCause());
			log.error(npe.initCause(npe));
			response.put("error", "Por favor inténtelo pasados unos minutos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (!lista.isEmpty()) {
			log.info("Lista populada");
			response.put("lista", lista);
		}else {
			log.info("La lista está vacia");
			response.put("mensaje", "Lista vacia");
		}
		
		log.info("Se devuelve el objeto response más el estado del HttpStatus");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	/**
	 * Metodo que muestra un conductor por su id
	 * @param conductorId de tipo Long
	 * @return ResponseEntity<Map<String, Object>>
	 */
	@GetMapping("/usuarios/conductor/{id}")
	public ResponseEntity<?> verConductorEmpresa(@PathVariable("id") Long conductorId) {
		log.info("Se pide ver un conductor de empresa");
		log.info("Se crea el objeto conductor con el id recogido como parámetro");
		ConductorEmpresa conductor = iConductorEmpService.verConductorEmpresa(conductorId);
		log.info("Objeto conductor creado");
		
		Map<String, Object> resp = new HashMap<String, Object>();
		
		if(conductor != null) {
			log.info("El usuario se puede mostrar");
			resp.put("conductor", conductor);
		}else {
			log.warn("El usuario no se puede mostrar");
			resp.put("error", "Usuario no encontrado");
		}
		
		log.info("Se envia la respuesta con el status correcto");
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
	}
	
	/**
	 * Metodo que muestra listado de conductores por id de usuario
	 * @param usuarioId de tipo Long
	 * @return ResponseEntity<Map<String, Object>>
	 */
	@GetMapping("/usuarios/conductores/usuario/{Id}")
	public ResponseEntity<?> verConductorEmpresaByUsuario(@PathVariable("Id") Long usuarioId) {
		log.info("Se pide que se listen los conductores de un usuario");
		Map<String, Object> resp = new HashMap<String, Object>();
		List<ConductorEmpresa> lista = new ArrayList<ConductorEmpresa>();
		
		try {
			log.info("Se popula la lista con los datos de la bbdd en base al usuarioId que se ha pasado");
			lista = iConductorEmpService.mostrarConductorByUsuario(usuarioId);
			if(lista.isEmpty()) {
				log.warn("Lista vacia, el usuario buscado no tiene conductores");
				resp.put("mensaje", "No se han encontrado registros");
				return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
			}else {
				log.info("Lista populada con éxito");
			}
			
			resp.put("lista", lista);
		}catch (NullPointerException npe) {
			log.error("error", "error: ".concat(npe.getMessage().concat(npe.getLocalizedMessage())));
			resp.put("error", "Se ha producido un error. Inténtelo más tarde");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		log.info("Se envia la respuesta con el estatus");
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
	}
	
	/**
	 * Metodo para dar de alta un conductor de un usuario empresa
	 * @param conductor de tipo ConductorEmpresa
	 * @return ResponseEntity<Map<String, Object>>
	 */
	@PostMapping("/usuarios/conductor/alta-conductor")
	public ResponseEntity<?> altaNuevoConductor(@RequestBody ConductorEmpresa conductor){
		log.info("Se va a dar de alta a un nuevo conductor");
		Map<String, Object> resp = new HashMap<String, Object>();
		log.info("Se crea el objeto conductor con el id recogido como parámetro");
		Usuario usuario = iUsuarioServices.showUsuarioById(conductor.getUsuario().getUsuarioId());
		
		if (usuario != null) {
			log.info("se asigna usuario al conductor");
			conductor.setUsuario(usuario);
		}
		
		try {
			log.info("Se da de alta al nuevo conductor");
			iConductorEmpService.nuevoConductorEmpresa(conductor);
			log.info("Alta satisfactoria");
			log.info("Cargamos la respuesta a devolver con el objeto conductor");
			resp.put("conductor", conductor);
			
		}catch (NullPointerException npe) {
			log.error("error: ".concat(npe.getMessage().concat(npe.getLocalizedMessage())));
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se envia la respuesta con el estatus");
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
	}
	
	/**
	 * Metodo para dar de alta un conductor de un usuario empresa
	 * @param conductor de tipo ConductorEmpresa
	 * @return ResponseEntity<Map<String, Object>>
	 */
	@PutMapping("/usuarios/conductor/modifica-conductor/{conductorId}")
	public ResponseEntity<?> modificaConductor(@RequestBody ConductorEmpresa conductor, @PathVariable Long conductorId){
		log.info("Se pide modificar al conductor con id " + conductorId);
		Map<String, Object> resp = new HashMap<String, Object>();
		
		log.info("Se crea el objeto conductor con el id recogido como parámetro");
		ConductorEmpresa conductorActual = iConductorEmpService.verConductorEmpresa(conductorId);
		
		if (conductorActual != null) {
			log.info("Se actualizan los datos del conductor existente con los nuevos datos");
			log.info("Se actualiza Alias");
			conductorActual.setConductorAlias(conductor.getConductorAlias());
			log.info("Se actualiza Nombre");
			conductorActual.setConductorNombre(conductor.getConductorNombre());
			log.info("Se actualiza Apellido1");
			conductorActual.setConductorApellido1(conductor.getConductorApellido1());
			log.info("Se actualiza Apellido2");
			conductorActual.setConductorApellido2(conductor.getConductorApellido2());
			log.info("Se actualiza password");
			conductorActual.setConductorPassword(conductor.getConductorPassword());
		} else {
			log.error("El usuario no existe en la base de datos");
			resp.put("error", "Usuario encontrado en la base de datos");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
		}
		
		try {
			log.info("Se actualiza Actualiza el conductor en la base de datos");
			iConductorEmpService.nuevoConductorEmpresa(conductorActual);
			log.info("Usuario Modificado con éxito");
			resp.put("conductor", conductorActual);
		}catch (NullPointerException npe) {
			log.error("error: ".concat(npe.getMessage().concat(npe.getLocalizedMessage())));
			resp.put("error", "Se ha producido un error. Inténtelo en unos minutos");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se envia la respuesta con el estatus");
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
	}
	
	/**
	 * Metodo para eliminar un Conductor de un usuario
	 * @param idUsuario de tipo Long
	 * @return Usuario Modificado
	 */
	@DeleteMapping("/usuario/conductor/elimina-conductor/{conductorId}")
	public ResponseEntity<?> eliminaConductor(@PathVariable Long conductorId) {
		log.info("Se va a eliminar al usuario id: " + conductorId);
		Map<String, Object> resp = new HashMap<String, Object>();
	
		try {
			log.info("Se elimina al conductor de la base de datos");
			iConductorEmpService.eliminaConductorEmpresa(conductorId);
			log.info("Conductor eliminado con éxito");
			resp.put("mensaje", "Usuario eliminado satisfactoriamente");
			
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(DataAccessException dae) {
			log.warn("Se busca al conductor en la base de datos");
			ConductorEmpresa ce = iConductorEmpService.verConductorEmpresa(conductorId);
			if (ce == null) {
				log.error("El conductor no figura en la base de datos");
			}else {
				log.error("Error al eliminar de la base de datos");
			}
			log.error("error", dae.getMessage().concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
			resp.put("mensaje", "Se haproducido un error al eliminar");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
}
