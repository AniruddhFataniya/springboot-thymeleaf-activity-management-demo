package com.rudraksh.springboot.mapper;

import com.rudraksh.springboot.web.dto.ActivityWithUser;
import com.rudraksh.springboot.web.dto.IActivityWithUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

public interface MapStructMapper {


    List<ActivityWithUser> iActivityWithUsertoActivityWithUser(List<IActivityWithUser> l);


}
