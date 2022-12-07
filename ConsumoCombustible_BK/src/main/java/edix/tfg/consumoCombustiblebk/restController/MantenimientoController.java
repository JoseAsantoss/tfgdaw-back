package edix.tfg.consumoCombustiblebk.restController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.server.ResponseStatusException;

import edix.tfg.consumoCombustiblebk.models.entity.Mantenimiento;
import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;
import edix.tfg.consumoCombustiblebk.services.IMantenimientoService;
import edix.tfg.consumoCombustiblebk.services.IUsuarioService;
import edix.tfg.consumoCombustiblebk.services.IVehiculoService;
import edix.tfg.consumoCombustiblebk.services.IVersionCocheService;
import lombok.extern.log4j.Log4j2;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/vehiculo")
@Log4j2
public class MantenimientoController {
	
	@Autowired
	IVehiculoService iVehiculoService;
	
	@Autowired
	IUsuarioService iUsuarioService;
	
	@Autowired
	IVersionCocheService iVersionCocheService;
	
	@Autowired
	IMantenimientoService iMantenimientoService;
	
	
	/**
	 * Ver Mantenimientos de un vehículo.
	 * 
	 * Opcionalmente se indican las fechas entre las que están 
	 * comprendidos los Mantenimientos como parámetros en la cabecera.
	 * 
	 * Alternativa, complementaria y opcionalmente permite filtrar con 
	 * búsqueda de los mantenimientos.
	 * 
	 * https://stackoverflow.com/a/60889337
	 */
	
	@Secured({
		"ROLE_PARTICULAR", 
		"ROLE_EMPRESA", 
		"ROLE_ADMIN"})
	@GetMapping("/{vehiculoId}/mantenimientos")
	public ResponseEntity<List<Mantenimiento>> MantenimientosVehiculo(
			@PathVariable Long vehiculoId,
			@RequestParam Map<String, String> params) 
					 throws ParseException{
		
		List<Mantenimiento> listaMantenimientos = new ArrayList<Mantenimiento>();		
		SimpleDateFormat fechaSDF = new SimpleDateFormat("yyyy-MM-dd");
				
		Date fechaInicioDate = null;
		Date fechaFinDate = null;
		
		if (params.size() == 0) { //sin parámetros
			try {
				listaMantenimientos = 
						iMantenimientoService.showByVehiculoId(vehiculoId);
			} catch (NullPointerException npe) {
				log.error(npe.getStackTrace());
				log.error(npe.getCause());
				log.error(npe.initCause(npe));
				return new ResponseEntity<>(
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		if (params.size() > 3) { //Aceptar máx 3 parámetros (2 fechas y búsqueda)
		      throw new ResponseStatusException(
		    		  HttpStatus.BAD_REQUEST, 
		    		  "Sólo se pueden incluir tres parámetros");
		}
		
		String fechaInicio = "1900-01-01";
		String fechaFin = "2200-12-31";
		
		if (params.containsKey("fechaInicio")) {
			fechaInicio = params.get("fechaInicio");
		}
		
		if (params.containsKey("fechaFin")) {
			fechaFin = params.get("fechaFin");
		}
		
		try {
			fechaInicioDate = fechaSDF.parse(fechaInicio);
			fechaFinDate = fechaSDF.parse(fechaFin);			
		} catch (NullPointerException npe) {
			log.error(npe.getStackTrace());
			log.error(npe.getCause());
			log.error(npe.initCause(npe));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (params.containsKey("buscar")) {
			String busqueda = params.get("buscar");
			listaMantenimientos = 
					iMantenimientoService.searchByVehiculoIdFechas(
					vehiculoId, 
					busqueda, 
					fechaInicioDate, 
					fechaFinDate);
		} else if  (params.containsKey("buscar-profundo")) {
			String busqueda = params.get("buscar-profundo");
			listaMantenimientos = 
					iMantenimientoService.searchConceptObsByVehiculoIdFechas(
							vehiculoId, 
							busqueda, 
							fechaInicioDate, 
							fechaFinDate);
		} else {
			listaMantenimientos = 
					iMantenimientoService.showByVehiculoIdAndDate(
							vehiculoId, 
							fechaInicioDate, 
							fechaFinDate);
		}
		
		
		if(!listaMantenimientos.isEmpty()) {
			log.info("Lista populada");
		} else {
			log.info("La lista está vacia");
		}
		
		log.info("Se devuelve la lista de Mantenimiento y el estado del HttpStatus");
		return new ResponseEntity<List<Mantenimiento>>(listaMantenimientos, HttpStatus.OK);
	}
		
	
	/**
	 * Añadir un Mantenimiento a un vehículo
	 */
	@Secured({
		"ROLE_PARTICULAR", 
		"ROLE_EMPRESA", 
		"ROLE_CONDUCTOR", 
		"ROLE_ADMIN"})
	@PostMapping(
			path = "/{vehiculoId}/nuevo-mantenimiento", 
	        consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Mantenimiento> addMantenimiento(
			@PathVariable Long vehiculoId,
			@RequestBody Mantenimiento mantenimientoJson) {

		try {
			
			Vehiculo vehiculoPath = iVehiculoService.detallesVehiculo(vehiculoId);
			if (vehiculoId != mantenimientoJson.getVehiculo().getVehiculoId()) {
				log.error("error", "No coincide el id del vehículo en el Path con el id del vehículo del JSON");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				mantenimientoJson.setVehiculo(vehiculoPath);
				iMantenimientoService.addMantenimiento(mantenimientoJson);
			}
			
		} catch (DataAccessException dae) {
			String message = dae.getMessage();
			message = message != null? message : "";
			log.error("error", "error: ".concat(message.concat(" - ").concat(dae.getLocalizedMessage())));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		log.info("Se devuelve el objeto response más el estado del HttpStatus");
		return new ResponseEntity<Mantenimiento>(mantenimientoJson, HttpStatus.OK);
	}
	
	
	/**
	 * Ver un mantenimiento concreto de un vehículo
	 */
	@Secured({
		"ROLE_PARTICULAR", 
		"ROLE_EMPRESA", 
		"ROLE_ADMIN"})
	@GetMapping("/{vehiculoId}/mantenimiento/{mantenimientoId}")
	public ResponseEntity<Mantenimiento> MantenimientoDetalle(
			@PathVariable Long vehiculoId, 
			@PathVariable Long mantenimientoId) {
		
		Mantenimiento mantenimiento = new Mantenimiento();
	
		try {
			System.out.println("Entra en mantenimientoDetalle");
			mantenimiento = iMantenimientoService.showMantenimiento(mantenimientoId);
		} catch (NullPointerException npe) {
			log.error(npe.getStackTrace());
			log.error(npe.getCause());
			log.error(npe.initCause(npe));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(mantenimiento != null) {
			log.info("El Mantenimiento se puede mostrar");
		} else {
			log.warn("El mantenimiento no se puede mostrar");
		}
		
		log.info("Devolver Mantenimiento y estado del HttpStatus");
		return new ResponseEntity<Mantenimiento>(mantenimiento, HttpStatus.OK);
	}	

	
	/**
	 * Borrar un mantenimiento concreto por su ID
	 */
	@Secured({
		"ROLE_PARTICULAR", 
		"ROLE_EMPRESA", 
		"ROLE_ADMIN"})
	@DeleteMapping("/{vehiculoId}/mantenimiento/{mantenimientoId}/borrar-mantenimiento")
	public ResponseEntity<Mantenimiento> eliminaMantenimiento(
			@PathVariable Long mantenimientoId) {
		
		log.info("Eliminar el Mantenimiento con id: " + mantenimientoId);
		
		Mantenimiento mantenimientoBorrar = iMantenimientoService.showMantenimiento(mantenimientoId);
		Vehiculo vehiculo = mantenimientoBorrar.getVehiculo();

		log.info("El vehículo del Mantenimiento  " + mantenimientoId + 
				" es el de matrícula " + vehiculo.getVehiculoMatricula());
			
		try {
			log.info("Borrar el Mantenimiento de la BBDD");
			iMantenimientoService.delMantenimiento(mantenimientoId);
			
			log.info("Mantenimiento eliminado con éxito");
			
			return new ResponseEntity<Mantenimiento>(mantenimientoBorrar, HttpStatus.OK);
			
		} catch(DataAccessException dae) {
			log.warn("Se busca Mantenimiento en la base de datos");
			Mantenimiento rep = iMantenimientoService.showMantenimiento(mantenimientoId);
			
			if (rep == null) {
				log.error("El Mantenimiento no existe en la base de datos");
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
	 * Modificar un Mantenimiento concreto por su ID
	 * @throws ParseException 
	 */
	@Secured({
		"ROLE_PARTICULAR", 
		"ROLE_EMPRESA", 
		"ROLE_ADMIN"})
	@PutMapping("/{vehiculoId}/mantenimiento/{mantenimientoId}/editar-mantenimiento")
	public ResponseEntity<Mantenimiento> modificarMantenimiento(
			@PathVariable Long mantenimientoId,
			@RequestBody Mantenimiento mantenimientoJson) throws ParseException {
		
		log.info("Modificar el Mantenimiento con id: " + mantenimientoId);
		
		Mantenimiento mantenimientoEditar = iMantenimientoService.showMantenimiento(mantenimientoId);
		Vehiculo vehiculo = mantenimientoEditar.getVehiculo();		

		System.out.println(mantenimientoEditar.getMantenimientoFecha());
		System.out.println(mantenimientoJson.getMantenimientoFecha());
		
		log.info("El vehículo del Mantenimiento " + mantenimientoId + 
				" es el " + vehiculo.getVersionCoche().getModeloCoche().getMarcaCoche().getMarcaNombre() + 
				" modelo " + vehiculo.getVersionCoche().getModeloCoche().getModeloNombre() + 
				" versión " + vehiculo.getVersionCoche().getVersionNombre() + 
				" con matrícula " + vehiculo.getVehiculoMatricula());
		
		if (mantenimientoEditar != null) {
			
			if (mantenimientoJson.getMantenimientoDetalle() != null) {
				log.info("Actualizar detalle de Mantenimiento");
				mantenimientoEditar.setMantenimientoDetalle(mantenimientoJson.getMantenimientoDetalle());
			}
			
			if (mantenimientoJson.getMantenimientoFecha() != null) {
				log.info("Actualizar fecha de Mantenimiento");
				mantenimientoEditar.setMantenimientoFecha(mantenimientoJson.getMantenimientoFecha());
			}
			
			if (mantenimientoJson.getMantenimientoImporte() != null) {
				log.info("Actualizar detalle de Mantenimiento");
				mantenimientoEditar.setMantenimientoImporte(mantenimientoJson.getMantenimientoImporte());
			}
			
			if (mantenimientoJson.getMantenimientoKM() != null) {
				log.info("Actualizar km de Mantenimiento");
				mantenimientoEditar.setMantenimientoKM(mantenimientoJson.getMantenimientoKM());
			}
			
			if (mantenimientoJson.getMantenimientoObservaciones() != null) {
				log.info("Actualizar detalle de Mantenimiento");
				mantenimientoEditar.setMantenimientoObservaciones(mantenimientoJson.getMantenimientoObservaciones());
			}
		}
	
		try {
			log.info("Modificar el Mantenimiento de la BBDD");
			iMantenimientoService.editMantenimiento(mantenimientoEditar);
			log.info("Mantenimiento modificado con éxito");					
			return new ResponseEntity<Mantenimiento>(mantenimientoEditar, HttpStatus.CREATED);
			
		} catch(DataAccessException dae) {		
			
			log.warn("Se busca mantenimiento en la base de datos");
			Mantenimiento rep = iMantenimientoService.showMantenimiento(mantenimientoId);
			
			if (rep == null) {
				log.error("El mantenimiento no existe en la base de datos");
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
