package com.movements.app.models.dao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.movements.app.models.entity.Task;

public interface ITaskDao extends CrudRepository<Task, Long>{
	
	@Query(nativeQuery = true, value="select * from tasks t "
			+ "left join tasks t2 on t.id = t2.maintask_fk "
			+ "left join task_informations i on  t.id = i.task_fk "
			+ "left join companies c on t.company_fk = c.id "
			+ "left join employees e on t.employee_fk = e.id "
			+ "where t.id = ?#{[0]}")
	public Task fetchByIdWithEmployeeWithCompanyWithTaskInformationWithInformationWithSubtask(Long id);
	
	
}
