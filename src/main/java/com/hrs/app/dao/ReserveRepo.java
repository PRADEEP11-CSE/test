package com.hrs.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrs.app.model.Reserve;

@Repository
public interface ReserveRepo extends JpaRepository<Reserve, Long>{


}
