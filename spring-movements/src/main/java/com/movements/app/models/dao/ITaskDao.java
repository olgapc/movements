package com.movements.app.models.dao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.movements.app.models.entity.Task;

public interface ITaskDao extends CrudRepository<Task, Long>{
	
	@Query("select t from Task t join fetch t.company c join fetch t.employee e join fetch t.taskInformations i join fetch i.information where t.id=?1")
	public Task fetchByIdWithEmployeeWithCompanyWithTaskInformationWithInformation(Long id);
	
}
