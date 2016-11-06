package com.spring.study.dao;


import com.spring.study.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorDao extends JpaRepository<Administrator, Integer> {
    @Query(value = "select count(*) from Administrator admin where admin.user_name = ? and admin.password = ?")
    Integer findAdminCount(String user_name,String password);

}
