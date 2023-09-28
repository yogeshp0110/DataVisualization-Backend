package com.maveric.datavisualization.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Login")
public class Login {

	@Id
	
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "login_id_seq")
    @SequenceGenerator(name = "login_id_seq", sequenceName = "login_id_seq", allocationSize = 1, initialValue = 100)
	private Long loginId;
	private Long userId;
	private LocalDateTime loginTime;
	private LocalDateTime logoutDateTime;
	
	
}
