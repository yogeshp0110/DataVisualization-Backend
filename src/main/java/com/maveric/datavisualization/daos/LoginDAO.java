package com.maveric.datavisualization.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maveric.datavisualization.entities.Login;

@Repository
public interface LoginDAO extends JpaRepository<Login, Long> {

	Optional<Login> findByloginIdAndUserId(Long loginId, Long userId);


	
	                 
}
