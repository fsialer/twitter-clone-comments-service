package com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.mapper;

import com.fernando.ms.comments.app.domain.models.User;
import com.fernando.ms.comments.app.infraestructure.adapter.output.restclient.models.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserRestClientMapper {
    User toUser(UserResponse user);
}
