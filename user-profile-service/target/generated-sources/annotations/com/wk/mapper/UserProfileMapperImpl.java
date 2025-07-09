package com.wk.mapper;

import com.wk.domain.UserProfile;
import com.wk.model.UserProfileDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-07T21:20:41+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24 (Oracle Corporation)"
)
@Component
public class UserProfileMapperImpl implements UserProfileMapper {

    @Override
    public UserProfileDto toDto(UserProfile userProfile) {
        if ( userProfile == null ) {
            return null;
        }

        UserProfileDto userProfileDto = new UserProfileDto();

        return userProfileDto;
    }
}
