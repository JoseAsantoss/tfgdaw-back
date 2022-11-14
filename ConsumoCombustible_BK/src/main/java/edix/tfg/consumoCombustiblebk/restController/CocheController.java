package edix.tfg.consumoCombustiblebk.restController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	
	@GetMapping("marca/{marcaId}/modelos")
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
}
