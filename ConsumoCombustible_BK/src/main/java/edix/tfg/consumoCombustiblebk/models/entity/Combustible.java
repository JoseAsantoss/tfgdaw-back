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
@Table(name="combustibles")
public class Combustible implements Serializable {

	@Id
	@Column(name="COMBUSTIBLE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long combustibleId;

	@Column(name="COMBUSTIBLE_NOMBRE")
	private String combustibleNombre;
	
	@Column(name="COMBUSTIBLE_UE")
	private String combustibleUE;
	
	private static final long serialVersionUID = 1L;

}
