package edix.tfg.consumoCombustiblebk.services;

import java.util.List;

import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;

public interface IVehiculoService {

	public Vehiculo altaVehiculoUsuario(Vehiculo vehiculo);
	public List<Vehiculo> listaVehiculosUsuario(Long usuarioId);
	public List<Vehiculo> busquedaVehiculosUsuario(Long usuarioId, String busqueda);
	public Vehiculo detallesVehiculo(Long vehiculoId);
}
