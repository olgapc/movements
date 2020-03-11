package com.movements.app.models.dao;
import org.springframework.data.repository.CrudRepository;

import com.movements.app.models.entity.Task;

public interface ITaskDao extends CrudRepository<Task, Long>{

}
