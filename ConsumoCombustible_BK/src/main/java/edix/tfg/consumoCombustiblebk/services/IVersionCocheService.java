package edix.tfg.consumoCombustiblebk.services;

import java.util.List;

import edix.tfg.consumoCombustiblebk.models.entity.VersionCoche;

public interface IVersionCocheService {

	public VersionCoche showByVersionId(Long versionId);
	public List<VersionCoche> listAllVersionesFromModelo(Long modeloId);
	public VersionCoche addVersion(VersionCoche version);
}
