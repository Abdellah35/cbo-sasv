package com.cbo.core.model;

import com.cbo.core.utility.FileUploadUtil;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authority")
public class AuthorityDB implements Serializable {
	@Id
	@SequenceGenerator(
			name = "authority_sequence",
			sequenceName = "authority_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "authority_sequence"
	)
	@Column(nullable = false, updatable = false, unique = true)
	private Long id;
	@ManyToOne
	private Employee employee;

	@ManyToOne
	private Division division;

	private Boolean isActive;

	private String createdAt;

	private String updatedAt;


	public AuthorityDB(Division division, Employee employee, Boolean isActive) {
		this.employee = employee;
		this.isActive = isActive;
		this.division = division;
	}

}
