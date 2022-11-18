package edix.tfg.consumoCombustiblebk.restController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edix.tfg.consumoCombustiblebk.models.entity.Mantenimiento;
import edix.tfg.consumoCombustiblebk.models.entity.MarcaCoche;
import edix.tfg.consumoCombustiblebk.models.entity.ModeloCoche;
import edix.tfg.consumoCombustiblebk.models.entity.Usuario;
import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;
import edix.tfg.consumoCombustiblebk.models.entity.VersionCoche;
import edix.tfg.consumoCombustiblebk.services.IMarcaCocheService;
import edix.tfg.consumoCombustiblebk.services.IModeloCocheService;
import edix.tfg.consumoCombustiblebk.services.IRepostajeService;
import edix.tfg.consumoCombustiblebk.services.IUsuarioService;
import edix.tfg.consumoCombustiblebk.services.IVehiculoService;
import edix.tfg.consumoCombustiblebk.services.IVersionCocheService;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/coches")
@Log4j2
public class CocheController {
	
	@Autowired
	IVersionCocheService iVersionCocheService;
	
	@Autowired
	IModeloCocheService iModeloCocheService;
	
	@Autowired
	IMarcaCocheService iMarcaCocheService;
	
	@GetMapping("/marcas")
	public ResponseEntity<?> listarMarcas(){
		
		List<MarcaCoche> listaMarcas = new ArrayList<>();
		
		try {
			listaMarcas = iMarcaCocheService.listAllMarcas();			
		} catch (NullPointerException npe) {
			log.error(npe.getStackTrace());
			log.error(npe.getCause());
			log.error(npe.initCause(npe));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<List<MarcaCoche>>(listaMarcas, HttpStatus.OK);
	}
	
	@PostMapping(
			path = "/marcas/nueva-marca", 
	        consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevaMarca(
			@RequestBody MarcaCoche marcaCocheJson
			){
		try {
			iMarcaCocheService.addMarcaCoche(marcaCocheJson);
		} catch (DataAccessException dae){
			log.error("error", "error: ".concat(dae.getMessage().concat(" - ").concat(dae.getLocalizedMessage())));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se devuelve el objeto response de "
				+ "/marcas/nueva-marca"
				+ " más el estado del HttpStatus");
		
		// Si devuelve vacío es que no la ha añadido porque está duplicada
		// al estar UNIQUE el nombre_marca en la BBDD
		return new ResponseEntity<MarcaCoche>(marcaCocheJson, HttpStatus.OK);
	}
	
	@DeleteMapping("/marca/{marcaId}")
	public ResponseEntity<?> borrarMarca(
			@PathVariable Long marcaId
			){
		MarcaCoche marcaBorrar = null;
		try {
			marcaBorrar = iMarcaCocheService.showByMarcaId(marcaId);
			iMarcaCocheService.deleteMarcaId(marcaId);
		} catch (DataAccessException dae){
			log.error("error", "error: ".concat(dae.getMessage().concat(" - ").concat(dae.getLocalizedMessage())));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se devuelve el objeto response de "
				+ "/marcas/nueva-marca"
				+ " más el estado del HttpStatus");
		
		// Si devuelve vacío es que no la ha añadido porque está duplicada
		// al estar UNIQUE el nombre_marca en la BBDD
		if(marcaBorrar == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<MarcaCoche>(marcaBorrar, HttpStatus.OK);
		}
	}
	
	@DeleteMapping(
			path = "/marcas", 
	        consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> borrarMarca(
			@RequestBody MarcaCoche marcaCocheJson
			){
		String nombreMarca = marcaCocheJson.getMarcaNombre();
		Long marcaId = marcaCocheJson.getMarcaId();
		
		MarcaCoche marcaAlmacenada = null;
		try {
			if (marcaId == null) {
				marcaAlmacenada = iMarcaCocheService.findMarcaByNombre(nombreMarca);
			} else {
				marcaAlmacenada = iMarcaCocheService.showByMarcaId(marcaId);
			}			
			iMarcaCocheService.deleteMarcaId(marcaAlmacenada.getMarcaId());
			
		} catch (DataAccessException dae){
			log.error("error", "error: ".concat(dae.getMessage().concat(" - ").concat(dae.getLocalizedMessage())));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se devuelve el objeto response de "
				+ "/marca para DELETE"
				+ " más el estado del HttpStatus");
		
		// Si devuelve vacío es que no la ha añadido porque está duplicada
		// al estar UNIQUE el nombre_marca en la BBDD
		return new ResponseEntity<MarcaCoche>(marcaAlmacenada, HttpStatus.OK);
	}
	
	@GetMapping("/marca/{marcaId}/modelos")
	public ResponseEntity<?> listarModelos(
			@PathVariable Long marcaId){
		
		List<ModeloCoche> listaModelos = new ArrayList<>();
		
		try {
			listaModelos = iModeloCocheService.listAllModelosFromMarca(marcaId);			
		} catch (NullPointerException npe) {
			log.error(npe.getStackTrace());
			log.error(npe.getCause());
			log.error(npe.initCause(npe));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<List<ModeloCoche>>(listaModelos, HttpStatus.OK);
	}
	
	@PostMapping(
			path = {"/marca/{marcaId}/nuevo-modelo",
					"/marcas/nuevo-modelo"}, 
	        consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevoModelo(
			@RequestBody ModeloCoche modeloCocheJson,
			@PathVariable(required = false) Long marcaId
			){
		
		// Obtener el nombre o ID de MarcaCoche que venga por JSON
		String nombreMarca = modeloCocheJson.getMarcaCoche().getMarcaNombre();
		Long marcaIdJson = modeloCocheJson.getMarcaCoche().getMarcaId();
		
		// Ver si entre los datos hay una MarcaCoche que tenga el mismo nombre.
		// Si no la hay, sacarla desde el ID
		MarcaCoche marcaDelModeloEnviada = iMarcaCocheService.findMarcaByNombre(nombreMarca);		
		if (marcaDelModeloEnviada == null) {
			marcaDelModeloEnviada = iMarcaCocheService.showByMarcaId(marcaIdJson);
		}
		marcaIdJson = marcaDelModeloEnviada.getMarcaId();
		
		// Si marca por path y JSON no coinciden, y ninguna es null, error
		if (marcaId != marcaIdJson && marcaId != null) {
			log.error("La marca de la ruta y la incluida en el modelo no coinciden");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		Long marcaIdDefinitiva = Optional.ofNullable(marcaId).orElse(marcaIdJson);
		
		// Si marca por path y JSON son ambas null, error
		if (marcaIdDefinitiva == null) {
			log.error("La marca de la ruta y la incluida en el modelo no coinciden");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		marcaDelModeloEnviada = iMarcaCocheService.showByMarcaId(marcaIdDefinitiva);
		modeloCocheJson.setMarcaCoche(marcaDelModeloEnviada);		

		try {
			iModeloCocheService.addModeloCoche(modeloCocheJson);
			
		} catch (DataAccessException dae){
			log.error("error", "error: ".concat(dae.getMessage().concat(" - ").concat(dae.getLocalizedMessage())));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		log.info("Se devuelve el objeto response de "
				+ "marca/"+ marcaId +"/nuevo-modelo"
				+ " más el estado del HttpStatus");
		// Si devuelve vacío es que no la ha añadido porque está duplicado 
		// el modelo en la misma marca, pues están UNIQUE en conjunto marca_id
		// y nombre_modelo
		return new ResponseEntity<ModeloCoche>(modeloCocheJson, HttpStatus.OK);
	}
	
	@GetMapping("/marca/{marcaId}/modelo/{modeloId}/versiones")
	public ResponseEntity<?> listarVersiones(
			@PathVariable Long marcaId,
			@PathVariable Long modeloId){
		
		List<VersionCoche> listaVersiones = new ArrayList<>();
		
		try {
			listaVersiones = iVersionCocheService.listAllVersionesFromModelo(modeloId);			
		} catch (NullPointerException npe) {
			log.error(npe.getStackTrace());
			log.error(npe.getCause());
			log.error(npe.initCause(npe));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<List<VersionCoche>>(listaVersiones, HttpStatus.OK);
	}
	

	
	@DeleteMapping("/marca/{marcaId}/modelo/{modeloId}")
	public ResponseEntity<?> borrarModelo(
			@PathVariable Long marcaId,
			@PathVariable Long modeloId
			){
		ModeloCoche modeloBorrar = null;
		try {
			modeloBorrar = iModeloCocheService.borrarByModeloId(modeloId);
		} catch (DataAccessException dae){
			log.error("error", "error: ".concat(dae.getMessage().concat(" - ").concat(dae.getLocalizedMessage())));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		log.info("Se devuelve el objeto response del "
				+ "Modelo borrado"
				+ " más el estado del HttpStatus");
		
		if(modeloBorrar == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			System.out.println(modeloBorrar);
			return new ResponseEntity<ModeloCoche>(modeloBorrar, HttpStatus.OK);
		}
	}
	
	@DeleteMapping(
			path = {"/marcas/borrar-modelo"}, 
	        consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> borrarModeloJson(
			@RequestBody ModeloCoche modeloCocheJson,
			@PathVariable(required = false) Long marcaId
			){
		
		// Obtener el ID de ModeloCoche que venga por JSON
		Long modeloIdJson = modeloCocheJson.getModeloId();
		
		// Ver si entre los datos hay una ModeloCoche que tenga el mismo nombre.
		// Si no la hay, sacarla desde el ID
		ModeloCoche modeloEnviado = iModeloCocheService.showByModeloId(modeloIdJson);		
		
		try {
			iModeloCocheService.borrarByModeloId(modeloIdJson);
			
		} catch (DataAccessException dae){
			log.error("error", "error: ".concat(dae.getMessage().concat(" - ").concat(dae.getLocalizedMessage())));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		log.info("Se devuelve el objeto response de "
				+ "marca/"+ marcaId +"/nuevo-modelo"
				+ " más el estado del HttpStatus");
		// Si devuelve vacío es que no la ha añadido porque está duplicado 
		// el modelo en la misma marca, pues están UNIQUE en conjunto marca_id
		// y nombre_modelo
		return new ResponseEntity<ModeloCoche>(modeloCocheJson, HttpStatus.OK);
	}
	
	
}
