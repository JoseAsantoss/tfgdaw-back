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
@Table(name="mantenimientos")
public class Mantenimiento implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mantenimientoId;
	
	private String mantenimientoDetalle;
	
	@Temporal(TemporalType.DATE)
	private Date mantenimientoFecha;
	
	private BigDecimal mantenimientoImporte;
	
	@Column(name="MANTENIMIENTO_KM")
	private Long mantenimientoKM;
	
	private String mantenimientoObservaciones;
	
	@ManyToOne
	@JoinColumn(name="VEHICULO_ID")
	private Vehiculo vehiculo;
	
	
	private static final long serialVersionUID = 1L;

}
