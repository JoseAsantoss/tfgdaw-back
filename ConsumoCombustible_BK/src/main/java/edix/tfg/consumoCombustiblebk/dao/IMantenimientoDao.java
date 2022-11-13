package edix.tfg.consumoCombustiblebk.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edix.tfg.consumoCombustiblebk.models.entity.Mantenimiento;

@Repository
public interface IMantenimientoDao extends JpaRepository<Mantenimiento, Long> {
	
	@Query("select mto from Mantenimiento mto "
			+ "where mto.vehiculo.vehiculoId = ?1")
	public List<Mantenimiento> mantenimientosByIdVehiculo(Long vehiculoId);
	
	@Query("select mto from Mantenimiento mto "
			+ "where mto.vehiculo.vehiculoId = ?1 "
			+ "AND mto.mantenimientoFecha >= ?2 "
			+ "AND mto.mantenimientoFecha <= ?3")
	public List<Mantenimiento> mantenimientosPorVehiculoIdFechas(Long vehiculoId, Date fechaInicio, Date fechaFin);

	@Query("select mto from Mantenimiento mto "
			+ "where mto.vehiculo.vehiculoId = ?1 "
			+ "AND mto.mantenimientoDetalle LIKE %?2%")
	public List<Mantenimiento> searchByVehiculoId(Long idVehiculo, String search);
		
	@Query("select mto from Mantenimiento mto "
			+ "where mto.vehiculo.vehiculoId = ?1 "
			+ "AND mto.mantenimientoDetalle LIKE %?2%"
			+ "AND mto.mantenimientoFecha >= ?3 "
			+ "AND mto.mantenimientoFecha <= ?4")
	public List<Mantenimiento> searchByVehiculoIdDate(Long idVehiculo, String search, Date fechaInicio, Date fechaFin);
	
	@Query("select mto from Mantenimiento mto "
			+ "where mto.vehiculo.vehiculoId = ?1 "
			+ "AND (mto.mantenimientoObservaciones LIKE %?2% "
			+ "OR mto.mantenimientoDetalle LIKE %?2%)")
	public List<Mantenimiento> searchDeepByVehiculoId(Long idVehiculo, String search);
	
	@Query("select mto from Mantenimiento mto "
			+ "where mto.vehiculo.vehiculoId = ?1 "
			+ "AND (mto.mantenimientoObservaciones LIKE %?2% "
			+ "OR mto.mantenimientoDetalle LIKE %?2%)"
			+ "AND mto.mantenimientoFecha >= ?3 "
			+ "AND mto.mantenimientoFecha <= ?4")
	public List<Mantenimiento> searchDeepByVehiculoIdDate(Long idVehiculo, String search, Date fechaInicio, Date fechaFin);
}
