package edix.tfg.consumoCombustiblebk.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edix.tfg.consumoCombustiblebk.models.entity.Vehiculo;

@Repository
public interface IVehiculoDao extends JpaRepository<Vehiculo, Long> {

}
