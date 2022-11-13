package edix.tfg.consumoCombustiblebk.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;


@Data
@Entity
@Table(name="repostajes")
public class Repostaje implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long repostajeId;
	
	@Temporal(TemporalType.DATE)
	private Date repostajeFecha;
	
	private BigDecimal repostajeImporte;
	
	@Column(name="REPOSTAJE_KM")
	private Long repostajeKM;
	
	private BigDecimal repostajeLitros;
	
	@ManyToOne
	@JoinColumn(name="VEHICULO_ID")
	private Vehiculo vehiculo;
	
	private static final long serialVersionUID = 1L;

}
