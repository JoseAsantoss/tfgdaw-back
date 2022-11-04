package edix.tfg.consumoCombustiblebk.services.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edix.tfg.consumoCombustiblebk.dao.IConductorEmpresaDao;
import edix.tfg.consumoCombustiblebk.models.entity.ConductorEmpresa;
import edix.tfg.consumoCombustiblebk.services.IConductorEmpresaService;

/**
 * Clase que implementea los metodos de la interfaz IConductorService
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 03/11/2022
 *
 */
@Service
public class ConductorEmpresaServiceImpl implements IConductorEmpresaService{
	
	@Autowired
	IConductorEmpresaDao iConductorEmpDao;

	/**
	 * Metodo para obtener un conductor por su ID
	 * @param idConductor de tipo Long
	 * @return El conductor buscado
	 */
	@Transactional(readOnly = true)
	@Override
	public ConductorEmpresa verConductorEmpresa(Long idConductor) {
		return iConductorEmpDao.findById(idConductor).orElse(null);
	}

	/**
	 * Metodo para dar de alta un nuevo conductor de empresa
	 * @param nuevoConductor de tipo ConductorEmpresa
	 * @return ConductorEmpresa
	 */
	@Override
	public ConductorEmpresa nuevoConductorEmpresa(ConductorEmpresa nuevoConductor) {
		return iConductorEmpDao.save(nuevoConductor);
	}

	/**
	 * Metodo para actualizar los datos de un conductor de empresa
	 * @param updateConductor de tipo ConductorEmpresa
	 * @return ConductorEmpresa
	 */
	@Override
	public ConductorEmpresa actualizaConductorEmpresa(ConductorEmpresa updateConductor) {
		return iConductorEmpDao.save(updateConductor);
	}

	/**
	 * Metodo para eliminar un conductor de empresa
	 */
	@Override
	public void eliminaConductorEmpresa(Long idConductor) {
		iConductorEmpDao.deleteById(idConductor);
		
	}

	/**
	 * Metodo que lista todos los usuarios con el rol conductor
	 */
	@Transactional(readOnly = true)
	@Override
	public List<ConductorEmpresa> mostrarTodos() {
		return (List<ConductorEmpresa>) iConductorEmpDao.findAll();
	}

	/**
	 * Metodo que lista todos los usuarios con el rol de conductor vinculados a un usuario
	 */
	@Override
	public List<ConductorEmpresa> mostrarConductorByUsuario(Long usuarioId) {
		return (List<ConductorEmpresa>) iConductorEmpDao.findConductorEmpresaByUsuario(usuarioId);
	}

}
