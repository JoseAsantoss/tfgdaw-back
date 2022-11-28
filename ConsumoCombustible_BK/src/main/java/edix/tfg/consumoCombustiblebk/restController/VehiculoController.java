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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edix.tfg.consumoCombustiblebk.models.entity.Usuario;
import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;
import edix.tfg.consumoCombustiblebk.models.entity.VersionCoche;
import edix.tfg.consumoCombustiblebk.services.IRepostajeService;
import edix.tfg.consumoCombustiblebk.services.IUsuarioService;
import edix.tfg.consumoCombustiblebk.services.IVehiculoService;
import edix.tfg.consumoCombustiblebk.services.IVersionCocheService;
import lombok.extern.log4j.Log4j2;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
@Log4j2
public class VehiculoController {
	
	@Autowired
	IVehiculoService iVehiculoService;
	
	@Autowired
	IUsuarioService iUsuarioService;
	
	@Autowired
	IVersionCocheService iVersionCocheService;
	
	@Autowired
	IRepostajeService iRepostajeService;
	
	@PostMapping("/usuario/{usuarioId}/nuevo-vehiculo")
	public ResponseEntity<?> altaVehiculoDeUsuario(
			@PathVariable Long usuarioId, 
			@RequestBody Vehiculo vehiculo) {
		
		Map<String, Object> resp = new HashMap<String, Object>();
		log.info("Se recogen los datos del usuario");
		Usuario usuario = iUsuarioService.showUsuarioById(usuarioId);

		log.info("Usuario " + usuario.getUsuarioNombre() + "da de alta el vehiculo con matriculo " + vehiculo.getVehiculoMatricula());

		log.info("se recogen los datos de las version de coche");
		VersionCoche version = iVersionCocheService.showByVersionId(vehiculo.getVersionCoche().getVersionId());
		
		if (version != null) {
			log.info("Version de vehiculo no es null y se asigna a vehiculo");
			vehiculo.setVersionCoche(version);
		}
		
		
		
		try {
			log.info("Se da de alta el vehiculo en la base de datos");
			iVehiculoService.altaVehiculoUsuario(vehiculo);
			resp.put("mensaje", "Alta satisfactoria");
			resp.put("vehiculo", vehiculo);
			log.info("Alta del vehiculo correcta");
			
			//asignar al usuario
			if (usuario != null) {
				log.info("Usuario no es null. Se obtiene su lista de vehículos y se añade el nuevo");
				usuario.getVehiculos().add(vehiculo);
				iUsuarioService.updateUsuario(usuario);
			}
		}catch (DataAccessException dae) {
			log.error("error", "error: ".concat(dae.getMessage().concat(" - ").concat(dae.getLocalizedMessage())));
			resp.put("error","Se ha producido un erro en el alta. Revise los datos e inténtelo más tarde");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se envia response y estatus");
		return new ResponseEntity<Vehiculo>(vehiculo, HttpStatus.CREATED);
	}
	
	
	/**
	 * Lista de vehículos del usuario
	 * 
	 * Búsqueda por matrícula de vehículos del usuario si envía parámetro matricula.
	 * Búsqueda por marca, modelo o versión si envía parámetro descripcion.
	 * 
	 */
	@GetMapping("/usuario/{usuarioId}/vehiculos")
	public ResponseEntity<?> busquedaVehiculosUsuario(
			@PathVariable Long usuarioId, 
			@RequestParam Map<String, String> params) 
					 throws ParseException {

		Map<String, Object> resp = new HashMap<String, Object>();
		List<Vehiculo> listaVehiculos = new ArrayList<Vehiculo>();
		
		if (params.size() == 0) {
			try {
				listaVehiculos = iVehiculoService.listaVehiculosUsuario(usuarioId);
			} catch (NullPointerException npe) {
				log.error(npe.getStackTrace());
				log.error(npe.getCause());
				log.error(npe.initCause(npe));
				resp.put("error", "Por favor inténtelo pasados unos minutos");
				return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		if (params.size() > 1) { //Aceptar solo un parámetro
		      throw new ResponseStatusException(
		    		  HttpStatus.BAD_REQUEST, 
		    		  "Sólo se puede incluir un parámetro");
		}
		
		if (params.containsKey("matricula")) {
			String matricula = params.get("matricula");
			try {
				listaVehiculos = iVehiculoService.busquedaVehiculosUsuarioMatricula(usuarioId, matricula);
			} catch (NullPointerException npe) {
				log.error(npe.getStackTrace());
				log.error(npe.getCause());
				log.error(npe.initCause(npe));
				resp.put("error", "Por favor inténtelo pasados unos minutos");
				return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		if (params.containsKey("descripcion")) {
			String descripcion = params.get("descripcion");
			try {
				listaVehiculos = iVehiculoService.busquedaVehiculosUsuarioVersion(usuarioId, descripcion);
			} catch (NullPointerException npe) {
				log.error(npe.getStackTrace());
				log.error(npe.getCause());
				log.error(npe.initCause(npe));
				resp.put("error", "Por favor inténtelo pasados unos minutos");
				return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		if (!listaVehiculos.isEmpty()) {
			log.info("Lista populada");
			resp.put("lista", listaVehiculos);
		} else {
			log.info("La lista está vacia");
			resp.put("mensaje", "Lista vacia");
		}

		log.info("Se devuelve el objeto response más el estado del HttpStatus");
		return new ResponseEntity<List<Vehiculo>>(listaVehiculos, HttpStatus.OK);
	}
	

	@GetMapping("/usuario/{usuarioId}/totalVehiculos")
	public ResponseEntity<?> contarVehiculosUsuario(
			@PathVariable Long usuarioId) 
					 throws ParseException {

		List<Vehiculo> listaVehiculos = new ArrayList<Vehiculo>();
		Integer totalVehiculos;
		
			try {
				listaVehiculos = iVehiculoService.listaVehiculosUsuario(usuarioId);
				totalVehiculos = listaVehiculos.size();
			} catch (NullPointerException npe) {
				log.error(npe.getStackTrace());
				log.error(npe.getCause());
				log.error(npe.initCause(npe));
				log.error("error", "Por favor inténtelo pasados unos minutos");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		log.info("Se devuelve el objeto response " + listaVehiculos.getClass().toString() + " más el estado del HttpStatus");
		return new ResponseEntity<Integer>(totalVehiculos, HttpStatus.OK);
	}
	
	
	/**
	 * Detalles de un vehículo
	 */
	@GetMapping("/vehiculo/{vehiculoId}")
	public ResponseEntity<?> vehiculoDetalle(
			@PathVariable Long vehiculoId) {

		Map<String, Object> resp = new HashMap<String, Object>();
		Vehiculo vehiculo = new Vehiculo();
		
		try {
			vehiculo = iVehiculoService.detallesVehiculo(vehiculoId);
		} catch (NullPointerException npe) {
			log.error(npe.getStackTrace());
			log.error(npe.getCause());
			log.error(npe.initCause(npe));
			resp.put("error", "Por favor inténtelo pasados unos minutos");
			return new ResponseEntity<Vehiculo>(vehiculo, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(vehiculo != null) {
			log.info("El vehículo se puede mostrar");
			resp.put("vehiculo", vehiculo);
		}else {
			log.warn("El vehículo no se puede mostrar");
			resp.put("error", "Vehículo no encontrado");
		}

		log.info("Se devuelve el objeto response más el estado del HttpStatus");
		//return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
		return new ResponseEntity<Vehiculo>(vehiculo, HttpStatus.OK);
	}	
}
