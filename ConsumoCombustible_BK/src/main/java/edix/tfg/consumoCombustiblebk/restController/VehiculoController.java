package edix.tfg.consumoCombustiblebk.restController;

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
import org.springframework.web.bind.annotation.RestController;

import edix.tfg.consumoCombustiblebk.models.entity.Usuario;
import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;
import edix.tfg.consumoCombustiblebk.models.entity.VersionCoche;
import edix.tfg.consumoCombustiblebk.services.IRepostajeService;
import edix.tfg.consumoCombustiblebk.services.IUsuarioService;
import edix.tfg.consumoCombustiblebk.services.IVehiculoService;
import edix.tfg.consumoCombustiblebk.services.IVersionCocheService;
import lombok.extern.log4j.Log4j2;

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
	
	@PostMapping("/usuario/nuevo-vehiculo")
	public ResponseEntity<?> altaVehiculoDeUsuario(@RequestBody Vehiculo vehiculo) {
		log.info("Usuario " + vehiculo.getUsuario().getUsuarioId() + "da de alta un nuevo vehiculo");
		Map<String, Object> resp = new HashMap<String, Object>();
		log.info("Se recogen los datos del usuario");
		Usuario usuario = iUsuarioService.showUsuarioById(vehiculo.getUsuario().getUsuarioId());
		log.info("se recogen los datos de las version de coche");
		VersionCoche version = iVersionCocheService.showByVersionId(vehiculo.getVersionCoche().getVersionId());
		
		if (usuario != null) {
			log.info("Usuario no es null y se asigna a vehiculo");
			vehiculo.setUsuario(usuario);
		}
		
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
				
		}catch (DataAccessException dae) {
			log.error("error", "error: ".concat(dae.getMessage().concat(" - ").concat(dae.getLocalizedMessage())));
			resp.put("error","Se ha producido un erro en el alta. Revise los datos e inténtelo más tarde");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se envia response y estatus");
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
	}
	
	
	/**
	 * Lista de vehículos del usuario
	 */
	@GetMapping("/usuario/{usuarioId}/vehiculos")
	public ResponseEntity<?> VehiculosUsuario(
			@PathVariable Long usuarioId) {

		Map<String, Object> resp = new HashMap<String, Object>();
		List<Vehiculo> listaVehiculos = new ArrayList<Vehiculo>();
		
		try {
			listaVehiculos = iVehiculoService.listaVehiculosUsuario(usuarioId);
		} catch (NullPointerException npe) {
			log.error(npe.getStackTrace());
			log.error(npe.getCause());
			log.error(npe.initCause(npe));
			resp.put("error", "Por favor inténtelo pasados unos minutos");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (!listaVehiculos.isEmpty()) {
			log.info("Lista populada");
			resp.put("lista", listaVehiculos);
		}else {
			log.info("La lista está vacia");
			resp.put("mensaje", "Lista vacia");
		}

		log.info("Se devuelve el objeto response más el estado del HttpStatus");
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
	}
	
	/**
	 * Detalles de un vehículo
	 */
	@GetMapping("/vehiculo/{vehiculoId}")
	public ResponseEntity<?> VehiculoDetalle(
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
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(vehiculo != null) {
			log.info("El vehículo se puede mostrar");
			resp.put("vehiculo", vehiculo);
		}else {
			log.warn("El vehículo no se puede mostrar");
			resp.put("error", "Vehículo no encontrado");
		}

		log.info("Se devuelve el objeto response más el estado del HttpStatus");
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
	}	
}
