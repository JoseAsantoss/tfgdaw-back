package edix.tfg.consumoCombustiblebk.restController;

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
import edix.tfg.consumoCombustiblebk.models.entity.Empresa;
import edix.tfg.consumoCombustiblebk.services.IEmpresaService;
import lombok.extern.log4j.Log4j2;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/empresas")
@Log4j2
public class EmpresaController {
	
	@Autowired
	IEmpresaService iEmpresaService;

	/**
	 * 
	 * @param empresa a guardar.
	 * @return empresa con el ID incrustado que ha sido asignado por la BBDD.
	 */
	@Secured({
		"ROLE_ADMIN"})
	@PostMapping("/nueva-empresa")
	public ResponseEntity<?> altaEmpresa(
			@RequestBody Empresa empresa) {
		
		try {
			log.info("Intento de dar de alta la empresa en la BBDD");
			empresa = iEmpresaService.altaEmpresa(empresa);
			log.info(
					"Alta de empresa con CIF " + 
					empresa.getCif() + 
					" y razón social " + empresa.getRazonSocial() + 
					" correcta con el ID " + empresa.getEmpresaId());
		} catch (DataAccessException dae) {
			String message = dae.getMessage();
			message = message != null? message : "";
			log.error("error", "error: ".concat(message.concat(" - ").concat(dae.getLocalizedMessage())));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se devuelve la empresa y estatus 201");
		return new ResponseEntity<Empresa>(empresa, HttpStatus.CREATED);
	}
	
	/**
	 * 
	 * @param empresaId es la ID de la empresa a buscar
	 * @return Empresa Es la empresa correspondiente al ID indicado.
	 */
	@Secured({
		"ROLE_EMPRESA", 
		"ROLE_CONDUCTOR", 
		"ROLE_ADMIN"})
	@GetMapping("/{empresaId}")
	public ResponseEntity<?> mostrarEmpresaId(
			@PathVariable Long empresaId) {
			
		Empresa empresa;
		try {
			empresa = iEmpresaService.buscarEmpresaId(empresaId);
		} catch (NullPointerException npe) {
			log.error(npe.getStackTrace());
			log.error(npe.getCause());
			log.error(npe.initCause(npe));
			log.error("No existe ninguna empresa en la BBDD con el ID " + empresaId);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se devuelve el objeto response más el estado del HttpStatus");
		return new ResponseEntity<Empresa>(empresa, HttpStatus.OK);
	}
	
	@Secured({
		"ROLE_EMPRESA", 
		"ROLE_CONDUCTOR", 
		"ROLE_ADMIN"})
	@GetMapping("/cif-{empresaCif}")
	public ResponseEntity<?> mostrarEmpresaCif(
			@PathVariable String empresaCif) {
			
		Empresa empresa;
		try {
			empresa = iEmpresaService.buscarEmpresaCif(empresaCif);
		} catch (NullPointerException npe) {
			log.error(npe.getStackTrace());
			log.error(npe.getCause());
			log.error(npe.initCause(npe));
			log.error("No se puede localizar ninguna empresa en la BBDD con el CIF " + empresaCif);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se devuelve el objeto response más el estado del HttpStatus");
		return new ResponseEntity<Empresa>(empresa, HttpStatus.OK);
	}
	
	@Secured({
		"ROLE_ADMIN"})
	@PutMapping("/{empresaId}")
	public ResponseEntity<?> modificarEmpresa(
			@PathVariable Long empresaId,
			@RequestBody Empresa empresa) {
		
		try {
			log.info("Intento de actualizar la empresa en la BBDD");
			Empresa empresaPrevia = iEmpresaService.buscarEmpresaId(empresaId);
			log.info(
					"Los datos de la empresa almacenada en la base de datos son: " +
					"\nCIF: " + empresaPrevia.getCif() + 
					"\nRazón social: " + empresaPrevia.getRazonSocial() + 
					"\nID: " + empresaPrevia.getEmpresaId());
			empresa = iEmpresaService.updateEmpresa(empresaId, empresa);
			log.info(
					"Los datos de la empresa actualizada en la base de datos son: " +
					"\nCIF: " + empresa.getCif() + 
					"\nRazón social: " + empresa.getRazonSocial() + 
					"\nID: " + empresa.getEmpresaId());
		} catch (DataAccessException dae) {
			String message = dae.getMessage();
			message = message != null? message : "";
			log.error("error", "error: ".concat(message.concat(" - ").concat(dae.getLocalizedMessage())));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se devuelve la empresa y estatus 201");
		return new ResponseEntity<Empresa>(empresa, HttpStatus.CREATED);
	}
	
	@Secured({
		"ROLE_ADMIN"})
	@DeleteMapping("/{empresaId}")
	public ResponseEntity<?> borrarEmpresa(
			@PathVariable Long empresaId) {
		
		try {
			log.info("Intento de borrar la empresa en la BBDD");
			Empresa empresaPrevia = iEmpresaService.buscarEmpresaId(empresaId);
			log.info(
					"Los datos de la empresa almacenada en la base de datos son: " +
					"\nCIF: " + empresaPrevia.getCif() + 
					"\nRazón social: " + empresaPrevia.getRazonSocial() + 
					"\nID: " + empresaPrevia.getEmpresaId());
			iEmpresaService.deleteEmpresa(empresaId);
			log.info(
					"Empresa borrada con éxito");
		} catch (DataAccessException dae) {
			String message = dae.getMessage();
			message = message != null? message : "";
			log.error("error", "error: ".concat(message.concat(" - ").concat(dae.getLocalizedMessage())));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se devuelve estatus 200");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
