package edix.tfg.consumoCombustiblebk.restController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	@GetMapping("/{vehiculoId}/mantenimientos")
	public ResponseEntity<?> MantenimientosVehiculo(
			@PathVariable Long vehiculoId,
			@RequestParam Map<String, String> params) 
					 throws ParseException{
		
		//Map<String, Object> resp = new HashMap<String, Object>();
		List<Mantenimiento> listaMantenimientos = new ArrayList<Mantenimiento>();		
		SimpleDateFormat fechaSDF = new SimpleDateFormat("yyyy-MM-dd");
				
		Date fechaInicioDate = null;
		Date fechaFinDate = null;
		
		if (params.size() == 0) { //sin parámetros
			try {
				listaMantenimientos = iMantenimientoService.showByVehiculoId(vehiculoId);
			} catch (NullPointerException npe) {
				log.error(npe.getStackTrace());
				log.error(npe.getCause());
				log.error(npe.initCause(npe));
				//resp.put("error", "Por favor inténtelo pasados unos minutos");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		if (params.size() > 3) { //Aceptar solo dos parámetros (dos fechas y cadena de búsqueda)
		      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sólo se pueden incluir tres parámetros");
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
			//resp.put("error", "Por favor inténtelo pasados unos minutos");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (params.containsKey("buscar")) {
			String busqueda = params.get("buscar");
			listaMantenimientos = iMantenimientoService.searchByVehiculoIdFechas(vehiculoId, busqueda, fechaInicioDate, fechaFinDate);
		} else if  (params.containsKey("buscar-profundo")) {
			String busqueda = params.get("buscar-profundo");
			listaMantenimientos = iMantenimientoService.searchConceptObsByVehiculoIdFechas(vehiculoId, busqueda, fechaInicioDate, fechaFinDate);
		} else {
			listaMantenimientos = iMantenimientoService.showByVehiculoIdAndDate(vehiculoId, fechaInicioDate, fechaFinDate);
		}
		
		
		if(!listaMantenimientos.isEmpty()) {
			log.info("Lista populada");
			//resp.put("listaMantenimientos", listaMantenimientos);
		} else {
			log.info("La lista está vacia");
			//resp.put("mensaje", "Lista vacia");
		}
		
		log.info("Se devuelve la lista de Mantenimiento y el estado del HttpStatus");
		return new ResponseEntity<List<Mantenimiento>>(listaMantenimientos, HttpStatus.OK);
	}
		
	
	/**
	 * Añadir un Mantenimiento a un vehículo
	 */
	@PostMapping(
			path = "/{vehiculoId}/nuevo-mantenimiento", 
	        consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addMantenimiento(
			@PathVariable Long vehiculoId,
			@RequestBody Mantenimiento mantenimientoJson) {
		
		//Map<String, Object> resp = new HashMap<String, Object>();
		
		try {
			iMantenimientoService.addMantenimiento(mantenimientoJson);
			
		} catch (DataAccessException dae) {
			log.error("error", "error: ".concat(dae.getMessage().concat(" - ").concat(dae.getLocalizedMessage())));
			//resp.put("error","Error al añadir mantenimiento. Revise los datos e inténtelo más tarde");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		log.info("Se devuelve el objeto response más el estado del HttpStatus");
		return new ResponseEntity<Mantenimiento>(mantenimientoJson, HttpStatus.OK);
	}
	
	
	/**
	 * Ver un mantenimiento concreto de un vehículo
	 */
	@GetMapping("/{vehiculoId}/mantenimiento/{mantenimientoId}")
	public ResponseEntity<?> MantenimientoDetalle(
			@PathVariable Long vehiculoId, 
			@PathVariable Long mantenimientoId) {
		
		//Map<String, Object> resp = new HashMap<String, Object>();
		Mantenimiento mantenimiento = new Mantenimiento();
	
		try {
			System.out.println("Entra en mantenimientoDetalle");
			mantenimiento = iMantenimientoService.showMantenimiento(mantenimientoId);
		} catch (NullPointerException npe) {
			log.error(npe.getStackTrace());
			log.error(npe.getCause());
			log.error(npe.initCause(npe));
			//resp.put("error", "Por favor inténtelo pasados unos minutos");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(mantenimiento != null) {
			log.info("El Mantenimiento se puede mostrar");
			//resp.put("Mantenimiento", mantenimiento);
		} else {
			log.warn("El mantenimiento no se puede mostrar");
			//resp.put("error", "Mantenimiento no encontrado");
		}
		
		log.info("Devolver Mantenimiento y estado del HttpStatus");
		return new ResponseEntity<Mantenimiento>(mantenimiento, HttpStatus.OK);
	}
	

	
	/**
	 * Borrar un mantenimiento concreto por su ID
	 */
	@DeleteMapping("/{vehiculoId}/mantenimiento/{mantenimientoId}/borrar-mantenimiento")
	public ResponseEntity<?> eliminaMantenimiento(
			@PathVariable Long mantenimientoId) {
		
		log.info("Eliminar el Mantenimiento con id: " + mantenimientoId);
		
		Mantenimiento mantenimientoBorrar = iMantenimientoService.showMantenimiento(mantenimientoId);
		Vehiculo vehiculo = mantenimientoBorrar.getVehiculo();

		log.info("El vehículo del Mantenimiento  " + mantenimientoId + 
				" es el de matrícula " + vehiculo.getVehiculoMatricula());
		
		
		//Map<String, Object> resp = new HashMap<String, Object>();
	
		try {
			log.info("Borrar el Mantenimiento de la BBDD");
			iMantenimientoService.delMantenimiento(mantenimientoId);
			
			log.info("Mantenimiento eliminado con éxito");
			//resp.put("mensaje", "Mantenimiento eliminado satisfactoriamente");
			
			return new ResponseEntity<Mantenimiento>(mantenimientoBorrar, HttpStatus.OK);
			
		} catch(DataAccessException dae) {
			log.warn("Se busca Mantenimiento en la base de datos");
			Mantenimiento rep = iMantenimientoService.showMantenimiento(mantenimientoId);
			
			if (rep == null) {
				log.error("El Mantenimiento no existe en la base de datos");
			} else {
				log.error("Error al eliminar de la base de datos");
			}
			
			log.error("error", dae.getMessage().concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
			//resp.put("mensaje", "Se ha producido un error al eliminar");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	/**
	 * Modificar un Mantenimiento concreto por su ID
	 */
	@PutMapping("/{vehiculoId}/mantenimiento/{mantenimientoId}/editar-mantenimiento")
	public ResponseEntity<?> modificarMantenimiento(
			@PathVariable Long mantenimientoId,
			@RequestBody Mantenimiento mantenimientoJson) {
		
		log.info("Modificar el Mantenimiento con id: " + mantenimientoId);
		
		Mantenimiento mantenimientoEditar = iMantenimientoService.showMantenimiento(mantenimientoId);
		Vehiculo vehiculo = mantenimientoEditar.getVehiculo();

		log.info("El vehículo del Mantenimiento " + mantenimientoId + 
				" es el " + vehiculo.getVersionCoche().getModelosCoche().getMarcaCoche().getMarcaNombre() + 
				" modelo " + vehiculo.getVersionCoche().getModelosCoche().getModeloNombre() + 
				" versión " + vehiculo.getVersionCoche().getVersionNombre() + 
				" con matrícula " + vehiculo.getVehiculoMatricula());
		
		mantenimientoJson.setMantenimientoId(mantenimientoId);
		
		//Map<String, Object> resp = new HashMap<String, Object>();
	
		try {
			log.info("Modificar el Mantenimiento de la BBDD");
			iMantenimientoService.editMantenimiento(mantenimientoJson);
			
			log.info("Mantenimiento modificado con éxito");
			//resp.put("mensaje", "Mantenimiento modificado satisfactoriamente");
			
			return new ResponseEntity<Mantenimiento>(mantenimientoEditar, HttpStatus.OK);
			
		} catch(DataAccessException dae) {		
			
			log.warn("Se busca mantenimiento en la base de datos");
			Mantenimiento rep = iMantenimientoService.showMantenimiento(mantenimientoId);
			
			if (rep == null) {
				log.error("El mantenimiento no existe en la base de datos");
			} else {
				log.error("Error al eliminar de la base de datos");
			}
			
			log.error("error", dae.getMessage().concat(":" ).concat(dae.getMostSpecificCause().getMessage()));
			//resp.put("mensaje", "Se ha producido un error al eliminar");
			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
}
