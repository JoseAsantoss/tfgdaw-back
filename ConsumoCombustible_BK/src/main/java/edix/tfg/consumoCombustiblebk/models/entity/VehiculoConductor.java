package edix.tfg.consumoCombustiblebk.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="vehiculos_conductor")
public class VehiculoConductor implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vehiculoConductorId;
	
	//bi-directional many-to-one association to ConductorEmpresa
	@ManyToOne
	@JoinColumn(name="CONDUCTOR_ID")
	private ConductorEmpresa conductor;

	//bi-directional many-to-one association to Vehiculo
	@ManyToOne
	@JoinColumn(name="VEHICULO_ID")
	private Vehiculo vehiculo;
	
	
	private static final long serialVersionUID = 1L;

}
