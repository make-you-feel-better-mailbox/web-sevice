package com.onetwo.webservice.dto.user;

import com.onetwo.webservice.dto.AccessTokenDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.Objects;

@Getter
public class UpdateUserRequestDto extends AccessTokenDto {

    private final String nickname;

    private final String email;

    private final String phoneNumber;

    public UpdateUserRequestDto(@NotEmpty String accessToken, String nickname, String email, String phoneNumber) {
        super(accessToken);
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UpdateUserRequestDto that = (UpdateUserRequestDto) o;
        return Objects.equals(nickname, that.nickname) && Objects.equals(email, that.email) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nickname, email, phoneNumber);
    }
}
