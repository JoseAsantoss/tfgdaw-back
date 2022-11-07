package edix.tfg.consumoCombustiblebk.services.impl;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edix.tfg.consumoCombustiblebk.dao.IMantenimientoDao;
import edix.tfg.consumoCombustiblebk.models.entity.Mantenimiento;
import edix.tfg.consumoCombustiblebk.services.IMantenimientoService;

/**
 * Clase que implementea los metodos de la interfaz IMantenimientoService
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 06/11/2022
 *
 */
@Service
public class MantenimientoServiceImpl implements IMantenimientoService{
	
	@Autowired
	IMantenimientoDao iMantenimientoDao;

	/**
	 * Mostrar los mantenimientos de un ID de vehículo
	 * @param idVehiculo de tipo Long
	 * @return Lista de mantenimientos del vehículo cuya id
	 * 		   ha sido enviada
	 */	
	@Transactional(readOnly = true)
	@Override
	public List<Mantenimiento> showByVehiculoId(Long idVehiculo) {
		return iMantenimientoDao.mantenimientosByIdVehiculo(idVehiculo);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Mantenimiento> showByVehiculoIdAndDate(Long vehiculoId, Date fechaInicio, Date fechaFin) {

		SimpleDateFormat fechaSDF = new SimpleDateFormat("yyyy-MM-dd");
		
		if (fechaInicio == null && fechaFin == null) {
			return iMantenimientoDao.mantenimientosByIdVehiculo(vehiculoId);
		}
		
		if (fechaInicio != null && fechaFin == null) {
			try {
				fechaFin = fechaSDF.parse("2200-12-31");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		if (fechaInicio == null && fechaFin != null) {
			try {
				fechaInicio = fechaSDF.parse("1900-01-01");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		return iMantenimientoDao.mantenimientosPorVehiculoIdFechas(vehiculoId, fechaInicio, fechaFin);
	}

	@Override
	public List<Mantenimiento> searchByVehiculoId(Long idVehiculo, String search) {
		return iMantenimientoDao.searchByVehiculoId(idVehiculo, search);
	}

	@Override
	public List<Mantenimiento> searchConceptObsByVehiculoId(Long idVehiculo, String search) {
		return iMantenimientoDao.searchDeepByVehiculoId(idVehiculo, search);
	}

	/**
	 * Añadir mantenimientos
	 * @param nuevoMantenimiento de tipo Mantenimiento
	 * @return Mantenimiento
	 */	
	@Override
	public Mantenimiento addMantenimiento(Mantenimiento nuevoMantenimiento) {
		return iMantenimientoDao.save(nuevoMantenimiento);
		
	}
	/**
	 * Eliminar mantenimiento por ID
	 * @param mantenimientoId de tipo Long
	 */	
	@Override
	public void delMantenimiento(Long mantenimientoId) {
		iMantenimientoDao.deleteById(mantenimientoId);		
	}

	/**
	 * Obtener detalles de un mantenimiento con su ID
	 * @param mantenimientoId de tipo Long
	 * @return Mantenimiento
	 */	
	@Transactional(readOnly = true)
	@Override
	public Mantenimiento showMantenimiento(Long mantenimientoId) {
		return iMantenimientoDao.findById(mantenimientoId).orElse(null);
	}

	/**
	 * Editar mantenimientos
	 * @param editarMantenimiento de tipo Mantenimiento
	 * @return Mantenimiento guardado
	 */	
	@Override
	public Mantenimiento editMantenimiento(Mantenimiento editarMantenimiento) {
		return iMantenimientoDao.save(editarMantenimiento);
	}


}
