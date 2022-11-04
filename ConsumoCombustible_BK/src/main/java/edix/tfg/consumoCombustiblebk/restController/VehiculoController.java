package edix.tfg.consumoCombustiblebk.restController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edix.tfg.consumoCombustiblebk.models.entity.Usuario;
import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;
import edix.tfg.consumoCombustiblebk.models.entity.VersionCoche;
import edix.tfg.consumoCombustiblebk.services.IUsuarioService;
import edix.tfg.consumoCombustiblebk.services.IVehiculoService;
import edix.tfg.consumoCombustiblebk.services.IVersionCocheService;

@RestController
@RequestMapping("/api")
public class VehiculoController {
	
	@Autowired
	IVehiculoService iVehiculoService;
	
	@Autowired
	IUsuarioService iUsuarioService;
	
	@Autowired
	IVersionCocheService iVersionCocheService;

	@PostMapping("/usuario/nuevo-vehiculo")
	public ResponseEntity<?> altaVehiculoDeUsuario(@RequestBody Vehiculo vehiculo) {
		Map<String, Object> resp = new HashMap<String, Object>();
		Usuario usuario = iUsuarioService.showUsuarioById(vehiculo.getUsuario().getUsuarioId());
		VersionCoche version = iVersionCocheService.showByVersionId(vehiculo.getVersionCoche().getVersionId());
		
		if (usuario != null) {
			vehiculo.setUsuario(usuario);
		}
		
		if (version != null) {
			vehiculo.setVersionCoche(version);
		}
		
		try {
			iVehiculoService.altaVehiculoUsuario(vehiculo);
			resp.put("mensaje", "Alta satisfactoria");
			resp.put("vehiculo", vehiculo);
				
		}catch (DataAccessException dae) {
			resp.put("error", "error: ".concat(dae.getMessage().concat(" - ").concat(dae.getLocalizedMessage())));
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
	}
}
