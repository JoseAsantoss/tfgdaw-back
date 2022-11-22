package edix.tfg.consumoCombustiblebk.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Lazy;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="versiones_coche")
public class VersionCoche implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long versionId;
	
	private String versionNombre;
	
	//uni-directional many-to-one association to Combustible
	@ManyToOne
	@JoinColumn(name="COMBUSTIBLE_ID")
	private Combustible combustible;

	//bi-directional many-to-one association to ModelosCoche
	@ManyToOne
	@JoinColumn(name="MODELO_ID")
	@Lazy(false)
	private ModeloCoche modelosCoche;
	
	
	private static final long serialVersionUID = 1L;

}
