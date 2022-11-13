package edix.tfg.consumoCombustiblebk.services.impl;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edix.tfg.consumoCombustiblebk.dao.IRepostajeDao;
import edix.tfg.consumoCombustiblebk.models.entity.Repostaje;
import edix.tfg.consumoCombustiblebk.services.IRepostajeService;

/**
 * Clase que implementea los metodos de la interfaz IRepostajeService
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 05/11/2022
 *
 */
@Service
public class RepostajeServiceImpl implements IRepostajeService{
	
	@Autowired
	IRepostajeDao iRepostajeDao;

	/**
	 * Mostrar los repostajes de un ID de vehículo
	 * @param idVehiculo de tipo Long
	 * @return Lista de repostajes del vehículo cuya id
	 * 		   ha sido enviada
	 */	
	@Transactional(readOnly = true)
	@Override
	public List<Repostaje> showByVehiculoId(Long idVehiculo) {
		return iRepostajeDao.repostajesByIdVehiculo(idVehiculo);
	}
	

	@Transactional(readOnly = true)
	@Override
	public List<Repostaje> showByVehiculoIdAndDate(Long vehiculoId, Date fechaInicio, Date fechaFin) {
		
		SimpleDateFormat fechaSDF = new SimpleDateFormat("yyyy-MM-dd");
		
		if (fechaInicio == null && fechaFin == null) {
			return iRepostajeDao.repostajesByIdVehiculo(vehiculoId);
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
		
		return iRepostajeDao.repostajeporVehiculoIdFechas(vehiculoId, fechaInicio, fechaFin);
		
	}

	/**
	 * Añadir repostajes
	 * @param nuevoRepostaje de tipo Repostaje
	 * @return Repostaje
	 */	
	@Override
	public Repostaje addRepostaje(Repostaje nuevoRepostaje) {
		return iRepostajeDao.save(nuevoRepostaje);
		
	}
	/**
	 * Eliminar repostaje por ID
	 * @param repostajeId de tipo Long
	 */	
	@Override
	public void delRepostaje(Long repostajeId) {
		iRepostajeDao.deleteById(repostajeId);		
	}

	/**
	 * Obtener detalles de un repostaje con su ID
	 * @param repostajeId de tipo Long
	 * @return Repostaje
	 */	
	@Transactional(readOnly = true)
	@Override
	public Repostaje showRepostaje(Long repostajeId) {
		return iRepostajeDao.findById(repostajeId).orElse(null);
	}

	/**
	 * Editar repostajes
	 * @param editarRepostaje de tipo Repostaje
	 * @return Repostaje guardado
	 */	
	@Override
	public Repostaje editRepostaje(Repostaje editarRepostaje) {
		return iRepostajeDao.save(editarRepostaje);
	}


}
