package com.practice.reddit.mapper;

import com.practice.reddit.entity.User;
import com.practice.reddit.model.CustomUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface User_CustomUserMapper {
   User mapToUser(CustomUser customUser);
}
