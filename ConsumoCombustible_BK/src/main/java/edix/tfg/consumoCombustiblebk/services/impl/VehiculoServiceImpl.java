package edix.tfg.consumoCombustiblebk.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edix.tfg.consumoCombustiblebk.dao.IVehiculoDao;
import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;
import edix.tfg.consumoCombustiblebk.services.IVehiculoService;

/**
 * Clase que implementa los metodos de la interfaz IVehiculoService
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 04/11/2022
 *
 */
@Service
public class VehiculoServiceImpl implements IVehiculoService {

	@Autowired
	IVehiculoDao iVehiculoDao;
	
	@Override
	public Vehiculo altaVehiculoUsuario(Vehiculo vehiculo) {
		return iVehiculoDao.save(vehiculo);
	}
	
}
