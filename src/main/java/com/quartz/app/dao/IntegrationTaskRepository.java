/**
 * 
 */
package com.quartz.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quartz.model.IntegrationTask;


/**
 * @author Haseeb
 *
 */
@Repository
public interface IntegrationTaskRepository extends JpaRepository<IntegrationTask, Long> {

}
