package com.maveric.datavisualization.daos;

import com.maveric.datavisualization.entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {

	public Optional<User> findByEmail(String email);
	
	@Query("from User where email=:email")
	public User getByUserEmail(String email);
    @Query("from User where contactNo=:contactNo and countryCode=:countryCode")
    public User getUserWithContactNo(String contactNo,String countryCode);

	

}
