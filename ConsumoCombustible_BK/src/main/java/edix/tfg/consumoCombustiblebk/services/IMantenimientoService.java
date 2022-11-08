package edix.tfg.consumoCombustiblebk.services;

import java.util.Date;
import java.util.List;

import edix.tfg.consumoCombustiblebk.models.entity.Mantenimiento;

public interface IMantenimientoService {

	public List<Mantenimiento> showByVehiculoId(Long idVehiculo);
	public List<Mantenimiento> showByVehiculoIdAndDate(Long vehiculoId, Date fechaInicio, Date fechaFin);
	public List<Mantenimiento> searchByVehiculoId(Long idVehiculo, String search);
	List<Mantenimiento> searchConceptObsByVehiculoIdFechas(Long idVehiculo, String search, Date fechaInicio,
			Date fechaFin);
	List<Mantenimiento> searchByVehiculoIdFechas(Long idVehiculo, String search, Date fechaInicio, Date fechaFin);
	public List<Mantenimiento> searchConceptObsByVehiculoId(Long idVehiculo, String search);
	public Mantenimiento addMantenimiento(Mantenimiento mantenimiento);
	public void delMantenimiento(Long mantenimientoId);
	public Mantenimiento showMantenimiento(Long mantenimientoId);
	public Mantenimiento editMantenimiento(Mantenimiento mantenimiento);
	
}
