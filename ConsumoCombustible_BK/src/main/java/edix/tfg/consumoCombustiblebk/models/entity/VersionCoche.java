package edix.tfg.consumoCombustiblebk.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
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
@NoArgsConstructor
@Entity
@Table(name="versiones_coche")
public class VersionCoche implements Serializable {

	@Id
	@Column(name="VERSION_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long versionId;

	@Column(name="VERSION_NOMBRE")
	private String versionNombre;
	
	//uni-directional many-to-one association to Combustible
	@ManyToOne
	@JoinColumn(name="COMBUSTIBLE_ID")
	@Lazy(false)
	private Combustible combustible;

	//bi-directional many-to-one association to ModelosCoche
	@ManyToOne
	@JoinColumn(name="MODELO_ID")
	@Lazy(false)
	private ModeloCoche modeloCoche;
	
	
	private static final long serialVersionUID = 1L;

}
