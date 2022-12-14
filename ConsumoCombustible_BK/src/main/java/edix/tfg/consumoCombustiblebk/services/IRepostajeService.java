package edix.tfg.consumoCombustiblebk.services;

import java.util.Date;
import java.util.List;

import edix.tfg.consumoCombustiblebk.models.entity.Repostaje;

public interface IRepostajeService {

	public List<Repostaje> showByVehiculoId(Long idVehiculo);
	public List<Repostaje> showByVehiculoIdAndDate(Long vehiculoId, Date fechaInicio, Date fechaFin);
	public Repostaje addRepostaje(Repostaje repostaje);
	public void delRepostaje(Long repostajeId);
	public Repostaje showRepostaje(Long repostajeId);
	public Repostaje editRepostaje(Repostaje repostaje);
}
