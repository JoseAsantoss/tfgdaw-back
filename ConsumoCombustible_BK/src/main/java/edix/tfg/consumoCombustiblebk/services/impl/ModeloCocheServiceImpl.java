package edix.tfg.consumoCombustiblebk.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edix.tfg.consumoCombustiblebk.dao.IModeloCocheDao;
import edix.tfg.consumoCombustiblebk.models.entity.MarcaCoche;
import edix.tfg.consumoCombustiblebk.models.entity.ModeloCoche;
import edix.tfg.consumoCombustiblebk.services.IMarcaCocheService;
import edix.tfg.consumoCombustiblebk.services.IModeloCocheService;

/**
 * Clase que implementea los metodos de la interfaz IVersionCocheService
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 13/11/2022
 *
 */
@Service
public class ModeloCocheServiceImpl implements IModeloCocheService {

	@Autowired
	IModeloCocheDao iModeloCocheDao;
	
	@Autowired
	IMarcaCocheService iMarcaCocheService;

	@Override
	public ModeloCoche showByModeloId(Long modeloId) {
		return iModeloCocheDao.getById(modeloId);
	}

	/*@Override
	public List<ModeloCoche> listAllModelosFromMarca(MarcaCoche marca) {
		return iModeloCocheDao.findByMarcaId(marca.getMarcaId());
	}*/

	@Override
	public List<ModeloCoche> listAllModelosFromMarca(Long marcaCocheId) {
		MarcaCoche marcaDelModelo = iMarcaCocheService.showByMarcaId(marcaCocheId);
		return iModeloCocheDao.findByMarcaId(marcaCocheId);
	}

	@Override
	public ModeloCoche addModeloCoche(ModeloCoche modeloCocheJson) {
		return iModeloCocheDao.save(modeloCocheJson);
	}
	
	


}
