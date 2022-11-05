package edix.tfg.consumoCombustiblebk.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;

@Repository
public interface IVehiculoDao extends JpaRepository<Vehiculo, Long> {
	
	@Query("select vh from Vehiculo vh where vh.usuario.usuarioId = ?1")
	public List<Vehiculo> findVehiculosByUsuario(Long usuarioId);

}
