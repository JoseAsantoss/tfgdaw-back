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
@Table(name="modelos_coche")
public class ModeloCoche implements Serializable {

	@Id
	@Column(name="MODELO_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long modeloId;

	@Column(name="MODELO_NOMBRE")
	private String modeloNombre;
	
	//bi-directional many-to-one association to MarcasCoche
	@ManyToOne
	@JoinColumn(name="MARCA_ID")
	@Lazy(false)
	//@ToString.Exclude
	private MarcaCoche marcaCoche;
	
	private static final long serialVersionUID = 1L;
	

}
