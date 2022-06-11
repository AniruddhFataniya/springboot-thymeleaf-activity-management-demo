package com.rudraksh.springboot.repository;

import java.util.Date;
import java.util.List;

import com.rudraksh.springboot.model.CustomUser;
import com.rudraksh.springboot.web.dto.ActivityWithUser;
import com.rudraksh.springboot.web.dto.IActivityWithUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rudraksh.springboot.model.Activity;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	@Query(value="Select u.first_name as firstName , u.last_name as lastName, a.activity_name as activityName, a.activity_desc as activityDesc, a.deadline_date as deadlineDate from user u inner join activity a on u.id=a.user_id order by a.deadline_date asc limit 20", nativeQuery=true)
	List<IActivityWithUser> listActivity();
	
	@Query(value="Select u.id, u.first_name , u.last_name, a.activity_name, a.activity_desc, a.deadline_date, a.user_id from user u inner join activity a on u.id=a.user_id where u.first_name like %:keyword% or u.last_name like %:keyword% or a.activity_name like %:keyword%", nativeQuery=true)
	List<Activity> listActivityByKeyword(@Param("keyword")String keyword);
	
	//@Query(value="Select activity.activity_name, activity.activity_desc, activity.deadline_date, activity.user_id, user.id from activity inner join user on user.id=activity.user_id", nativeQuery=true)
	//List<Activity> listMyActivity();
	
	@Query(value="Select activity.id,activity.activity_name, activity.activity_desc, activity.deadline_date, activity.user_id from activity where user_id =?", nativeQuery=true)
	List<Activity> listMyActivity(Long id);

	List<Activity> findByUser(CustomUser user);
	
//
}
