package com.spring.study.dao;


import com.spring.study.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorDao extends JpaRepository<Administrator, Integer> {
    /*查询实体*/
    Administrator findAdministratorByUser_name(String user_name);
}
