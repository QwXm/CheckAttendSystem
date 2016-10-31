package com.spring.study.dao;

import com.spring.study.entity.Sign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/10/24.
 */
@Repository
public interface  SignDao extends JpaRepository<Sign,Integer>{

}
