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
@Table(name="modelos_coche")
public class ModeloCoche implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long modeloId;
	
	private String modeloNombre;
	
	//bi-directional many-to-one association to MarcasCoche
	@ManyToOne
	@JoinColumn(name="MARCA_ID")
	private MarcaCoche marcasCoche;
	
	private static final long serialVersionUID = 1L;

}
