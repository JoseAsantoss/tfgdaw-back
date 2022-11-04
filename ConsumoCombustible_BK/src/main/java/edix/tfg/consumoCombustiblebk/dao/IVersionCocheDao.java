package edix.tfg.consumoCombustiblebk.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edix.tfg.consumoCombustiblebk.models.entity.VersionCoche;

@Repository
public interface IVersionCocheDao extends JpaRepository<VersionCoche, Long> {

}
