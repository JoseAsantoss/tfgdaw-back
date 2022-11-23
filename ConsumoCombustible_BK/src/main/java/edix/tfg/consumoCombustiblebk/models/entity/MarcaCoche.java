package edix.tfg.consumoCombustiblebk.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="marcas_coche")
public class MarcaCoche implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="MARCA_ID")
	private Long marcaId;
	
	@Column(name="MARCA_NOMBRE")
	private String marcaNombre;
	
	private static final long serialVersionUID = 1L;

}
