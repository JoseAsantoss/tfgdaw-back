package edix.tfg.consumoCombustiblebk.services;

import java.util.List;

import edix.tfg.consumoCombustiblebk.models.entity.Mantenimiento;

public interface IMantenimientoService {

	public List<Mantenimiento> showByVehiculoId(Long idVehiculo);
	public Mantenimiento addMantenimiento(Mantenimiento mantenimiento);
	public void delMantenimiento(Long mantenimientoId);
	public Mantenimiento showMantenimiento(Long mantenimientoId);
	public Mantenimiento editMantenimiento(Mantenimiento mantenimiento);
}
