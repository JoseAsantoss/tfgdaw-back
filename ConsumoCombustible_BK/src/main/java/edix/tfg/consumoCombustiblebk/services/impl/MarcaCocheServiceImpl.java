package edix.tfg.consumoCombustiblebk.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edix.tfg.consumoCombustiblebk.dao.IMarcaCocheDao;
import edix.tfg.consumoCombustiblebk.models.entity.MarcaCoche;
import edix.tfg.consumoCombustiblebk.services.IMarcaCocheService;

/**
 * Clase que implementea los metodos de la interfaz IVersionCocheService
 * @author Luis Cifuentes
 * @author Jose A. Santos
 * @version 1.0
 * @since 13/11/2022
 *
 */
@Service
public class MarcaCocheServiceImpl implements IMarcaCocheService {

	@Autowired
	IMarcaCocheDao iMarcaCocheDao;
	
	@Override
	public MarcaCoche showByMarcaId(Long marcaId) {
		return iMarcaCocheDao.findById(marcaId).orElse(null);
	}


	@Override
	public String showNombreMarcaById(Long marcaId) {
		MarcaCoche marcaNoEncontrada = new MarcaCoche();
		marcaNoEncontrada.setMarcaNombre("Marca no encontrada");
		
		return iMarcaCocheDao.findById(marcaId).orElse(marcaNoEncontrada).getMarcaNombre();
	}
	
	@Override
	public MarcaCoche findMarcaByNombre(String nombreMarca) {
		return iMarcaCocheDao.findByNombre(nombreMarca);
	}

	@Override
	public List<MarcaCoche> listAllMarcas() {
		return iMarcaCocheDao.findAll();
	}

	@Override
	public MarcaCoche addMarcaCocheString(String nombreMarca) {
		MarcaCoche nuevaMarca = new MarcaCoche();
		nuevaMarca.setMarcaNombre(nombreMarca);
		
		return iMarcaCocheDao.save(nuevaMarca);
	}

	@Override
	public MarcaCoche addMarcaCoche(MarcaCoche marca) {
		return iMarcaCocheDao.save(marca);
	}


	@Override
	public MarcaCoche deleteMarcaId(Long marcaId) {
		if(iMarcaCocheDao.existsById(marcaId)) {
			MarcaCoche marcaBorrar = iMarcaCocheDao.findById(marcaId).orElse(null);
			iMarcaCocheDao.delete(marcaBorrar);
			return marcaBorrar;
		} else
			return null;
	}


}
