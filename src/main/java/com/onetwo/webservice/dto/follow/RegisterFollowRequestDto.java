package com.onetwo.webservice.dto.follow;

import com.onetwo.webservice.dto.AccessTokenDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.Objects;

@Getter
public class RegisterFollowRequestDto extends AccessTokenDto {

    @NotEmpty
    private final String targetUserId;

    public RegisterFollowRequestDto(@NotEmpty String accessToken, String targetUserId) {
        super(accessToken);
        this.targetUserId = targetUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RegisterFollowRequestDto that = (RegisterFollowRequestDto) o;
        return Objects.equals(targetUserId, that.targetUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), targetUserId);
    }
}
