package edix.tfg.consumoCombustiblebk.services;

import java.util.List;

import edix.tfg.consumoCombustiblebk.models.entity.MarcaCoche;

public interface IMarcaCocheService {

	public MarcaCoche showByMarcaId(Long versionId);
	public String showNombreMarcaById(Long versionId);
	public List<MarcaCoche> listAllMarcas();
	public MarcaCoche addMarcaCoche(MarcaCoche marca);
	public MarcaCoche addMarcaCocheString(String nombreMarca);
}
