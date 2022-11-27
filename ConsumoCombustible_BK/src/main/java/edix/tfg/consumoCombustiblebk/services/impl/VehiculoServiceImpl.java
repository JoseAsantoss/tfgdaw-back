package edix.tfg.consumoCombustiblebk.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edix.tfg.consumoCombustiblebk.dao.IUsuarioDao;
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
	
	@Autowired
	IUsuarioDao iUsuarioDao;
	
	@Override
	public Vehiculo altaVehiculoUsuario(Vehiculo vehiculo) {
		return iVehiculoDao.save(vehiculo);
	}

	
	@Override
	public List<Vehiculo> busquedaVehiculosUsuarioMatricula(Long usuarioId, String buscarMatricula) {
		System.out.println("entra en busquedaVehiculosUsuarioMatricula");
		return iUsuarioDao.listarVehiculosUsuario(usuarioId, buscarMatricula);
	}

	@Override
	public List<Vehiculo> busquedaVehiculosUsuario(Long usuarioId, String marcaModeloVersion) {
		return iUsuarioDao.listarVehiculosUsuario(usuarioId);
	}

	@Override
	public List<Vehiculo> listaVehiculosUsuario(Long usuarioId) {
		return iUsuarioDao.findById(usuarioId).orElse(null).getVehiculos();
	}
	
	@Override
	public Vehiculo detallesVehiculo(Long vehiculoId) {
		return iVehiculoDao.findById(vehiculoId).orElse(null);
	}

	@Override
	public List<Vehiculo> busquedaVehiculosUsuarioVersion(Long usuarioId, String descripcion) {
		return iUsuarioDao.listarVehiculosUsuarioVersion(usuarioId, descripcion);
	}
	
}
