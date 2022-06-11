package com.rudraksh.springboot.mapper;

import com.rudraksh.springboot.web.dto.ActivityWithUser;
import com.rudraksh.springboot.web.dto.IActivityWithUser;


import java.util.List;

public interface CustomMapper {


    List<ActivityWithUser> iActivityWithUsertoActivityWithUser(List<IActivityWithUser> l);


}
