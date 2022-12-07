package edix.tfg.consumoCombustiblebk.restController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import edix.tfg.consumoCombustiblebk.models.entity.Repostaje;
import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;
import edix.tfg.consumoCombustiblebk.services.IRepostajeService;
import edix.tfg.consumoCombustiblebk.services.IUsuarioService;
import edix.tfg.consumoCombustiblebk.services.IVehiculoService;
import edix.tfg.consumoCombustiblebk.services.IVersionCocheService;
import lombok.extern.log4j.Log4j2;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/vehiculo")
@Log4j2
public class RepostajeController {
	
	@Autowired
	IVehiculoService iVehiculoService;
	
	@Autowired
	IUsuarioService iUsuarioService;
	
	@Autowired
	IVersionCocheService iVersionCocheService;
	
	@Autowired
	IRepostajeService iRepostajeService;
	
	
	/**
	 * Ver repostajes de un vehículo.
	 * Opcionalmente se indican las fechas entre las que están 
	 * comprendidos los repostajes como parámetros en la cabecera.
	 */
	@Secured({
		"ROLE_PARTICULAR", 
		"ROLE_EMPRESA", 
		"ROLE_ADMIN"})
	@GetMapping("/{vehiculoId}/repostajes")
	public ResponseEntity<List<Repostaje>> RepostajesVehiculo(
			@PathVariable Long vehiculoId,
			@RequestParam(required = false) String fechaInicio,
			@RequestParam(required = false) String fechaFin) 
					 throws ParseException{
		
		List<Repostaje> listaRepostajes = new ArrayList<Repostaje>();
		
		SimpleDateFormat fechaSDF = new SimpleDateFormat("yyyy-MM-dd");
		
		Date fechaInicioDate = null;
		Date fechaFinDate = null;
		
		if (fechaInicio != null) {
			fechaInicioDate = fechaSDF.parse(fechaInicio);
		} 
		if (fechaFin != null) {
			fechaFinDate = fechaSDF.parse(fechaFin);
		}
	
		try {
			listaRepostajes = iRepostajeService.showByVehiculoIdAndDate(vehiculoId, fechaInicioDate, fechaFinDate);
		} catch (NullPointerException npe) {
			log.error(npe.getStackTrace());
			log.error(npe.getCause());
			log.error(npe.initCause(npe));
			log.error("Por favor inténtelo pasados unos minutos");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(!listaRepostajes.isEmpty()) {
			log.info("Lista populada");
		} else {
			log.info("La lista está vacia");
		}
		
		log.info("Se devuelve el objeto response más el estado del HttpStatus");
		return new ResponseEntity<List<Repostaje>>(listaRepostajes, HttpStatus.OK);
	}
	
	
	/**
	 * Añadir un repostaje a un vehículo
	 */
	@Secured({
		"ROLE_PARTICULAR", 
		"ROLE_EMPRESA", 
		"ROLE_CONDUCTOR", 
		"ROLE_ADMIN"})
	@PostMapping(
			path = "/{vehiculoId}/nuevo-repostaje", 
	        consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Repostaje> addRepostaje(
			@PathVariable Long vehiculoId,
			@RequestBody Repostaje repostajeNuevo) {
		
		//Map<String, Object> resp = new HashMap<String, Object>();
		
		try {
			iRepostajeService.addRepostaje(repostajeNuevo);
			
		} catch (DataAccessException dae) {
			String message = dae.getMessage();
			message = message != null? message : "";
			log.error("error", "error: ".concat(message.concat(" - ").concat(dae.getLocalizedMessage())));
			//resp.put("error","Error al añadir repostaje. Revise los datos e inténtelo más tarde");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		log.info("Se devuelve el objeto response más el estado del HttpStatus");
		return new ResponseEntity<Repostaje>(repostajeNuevo, HttpStatus.OK);
	}
	
	
	/**
	 * Ver un repostaje concreto de un vehículo
	 */
	@Secured({
		"ROLE_PARTICULAR", 
		"ROLE_EMPRESA", 
		"ROLE_ADMIN"})
	@GetMapping("/{vehiculoId}/repostaje/{repostajeId}")
	public ResponseEntity<Repostaje> RepostajeDetalle(
			@PathVariable Long vehiculoId, 
			@PathVariable Long repostajeId) {
		
		//Map<String, Object> resp = new HashMap<String, Object>();
		Repostaje repostaje = new Repostaje();
	
		try {
			System.out.println("Entra en RepostajeDetalle");
			repostaje = iRepostajeService.showRepostaje(repostajeId);
		} catch (NullPointerException npe) {
			log.error(npe.getStackTrace());
			log.error(npe.getCause());
			log.error(npe.initCause(npe));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(repostaje != null) {
			log.info("El repostaje se puede mostrar");
		} else {
			log.warn("El repostaje no se puede mostrar");
		}
		
		log.info("Se devuelve el objeto response más el estado del HttpStatus");
		return new ResponseEntity<Repostaje>(repostaje, HttpStatus.OK);
	}	

	
	/**
	 * Borrar un repostaje concreto por su ID
	 */
	@Secured({
		"ROLE_PARTICULAR", 
		"ROLE_EMPRESA", 
		"ROLE_ADMIN"})
	@DeleteMapping("/{vehiculoId}/repostaje/{repostajeId}/borrar-repostaje")
	public ResponseEntity<Repostaje> eliminaRepostaje(
			@PathVariable Long repostajeId) {
		
		log.info("Eliminar el repostaje con id: " + repostajeId);
		
		Repostaje repostajeBorrar = iRepostajeService.showRepostaje(repostajeId);		
		Vehiculo vehiculo = repostajeBorrar.getVehiculo();

		log.info("El vehículo del repostaje  " + repostajeId + 
				" es el de matrícula " + vehiculo.getVehiculoMatricula());
	
		try {
			log.info("Borrar el repostaje de la BBDD");
			iRepostajeService.delRepostaje(repostajeId);
			
			log.info("Repostaje eliminado con éxito");		
			return new ResponseEntity<Repostaje>(repostajeBorrar, HttpStatus.OK);
			
		} catch(DataAccessException dae) {
			log.warn("Se busca repostaje en la base de datos");
			Repostaje rep = iRepostajeService.showRepostaje(repostajeId);
			
			if (rep == null) {
				log.error("El repostaje no existe en la base de datos");
			} else {
				log.error("Error al eliminar de la base de datos");
			}

			String message = dae.getMessage();
			message = message != null? message : "";
			log.error("error", message.concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	/**
	 * Modificar un repostaje concreto por su ID
	 */
	@Secured({
		"ROLE_PARTICULAR", 
		"ROLE_EMPRESA", 
		"ROLE_ADMIN"})
	@PutMapping("/{vehiculoId}/repostaje/{repostajeId}/editar-repostaje")
	public ResponseEntity<Repostaje> modificarRepostaje(
			@PathVariable Long repostajeId,
			@RequestBody Repostaje repostajeJson) {
		
		log.info("Modificar el repostaje con id: " + repostajeId);
		Repostaje repostajePrevio = iRepostajeService.showRepostaje(repostajeId);
		Vehiculo vehiculo = repostajePrevio.getVehiculo();

		log.info("El vehículo del repostaje " + repostajeId + 
				" es el " + vehiculo.getVersionCoche().getModeloCoche().getMarcaCoche().getMarcaNombre() + 
				" modelo " + vehiculo.getVersionCoche().getModeloCoche().getModeloNombre() + 
				" versión " + vehiculo.getVersionCoche().getVersionNombre() + 
				" con matrícula " + vehiculo.getVehiculoMatricula());
		
		repostajeJson.setRepostajeId(repostajeId);
		repostajeJson.setVehiculo(vehiculo);
	
		try {
			log.info("Modificar el repostaje de la BBDD");
			iRepostajeService.editRepostaje(repostajeJson);
			
			log.info("Repostaje modificado con éxito");
			
			return new ResponseEntity<Repostaje>(repostajeJson, HttpStatus.OK);
			
		} catch(DataAccessException dae) {		
			
			log.warn("Se busca repostaje en la base de datos");
			Repostaje rep = iRepostajeService.showRepostaje(repostajeId);
			
			if (rep == null) {
				log.error("El repostaje no existe en la base de datos");
			} else {
				log.error("Error al eliminar de la base de datos");
			}

			String message = dae.getMessage();
			message = message != null? message : "";
			log.error("error", message.concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
