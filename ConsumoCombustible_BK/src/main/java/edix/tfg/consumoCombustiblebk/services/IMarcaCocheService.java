package edix.tfg.consumoCombustiblebk.services;

import java.util.List;

import edix.tfg.consumoCombustiblebk.models.entity.MarcaCoche;

public interface IMarcaCocheService {

	public MarcaCoche showByMarcaId(Long versionId);
	public List<MarcaCoche> listAllMarcas();
}
