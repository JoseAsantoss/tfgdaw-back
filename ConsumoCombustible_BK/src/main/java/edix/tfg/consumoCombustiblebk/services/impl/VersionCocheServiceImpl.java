package edix.tfg.consumoCombustiblebk.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edix.tfg.consumoCombustiblebk.dao.IVersionCocheDao;
import edix.tfg.consumoCombustiblebk.models.entity.VersionCoche;
import edix.tfg.consumoCombustiblebk.services.IVersionCocheService;

/**
 * Clase que implementea los metodos de la interfaz IVersionCocheService
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 04/11/2022
 *
 */
@Service
public class VersionCocheServiceImpl implements IVersionCocheService {

	@Autowired
	IVersionCocheDao iVersionCocheDao;
	
	@Override
	public VersionCoche showByVersionId(Long versionId) {
		return iVersionCocheDao.findById(versionId).orElse(null);
	}

	@Override
	public List<VersionCoche> listAllVersionesFromModelo(Long modeloId) {
		return iVersionCocheDao.findByModeloId(modeloId);
	}
	
	@Override
	public VersionCoche addVersion(VersionCoche version) {
		return iVersionCocheDao.save(version);
	}

}
