package edix.tfg.consumoCombustiblebk.services.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edix.tfg.consumoCombustiblebk.dao.ICombustibleDao;
import edix.tfg.consumoCombustiblebk.services.ICombustibleService;

/**
 * Clase que implementea los metodos de la interfaz IMantenimientoService
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 06/11/2022
 *
 */
@Service
public class CombustibleServiceImpl implements ICombustibleService{
	
	@Autowired
	ICombustibleDao iCombustibleDao;



}
