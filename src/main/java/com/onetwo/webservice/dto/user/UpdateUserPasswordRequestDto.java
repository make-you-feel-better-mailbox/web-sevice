package com.onetwo.webservice.dto.user;

import com.onetwo.webservice.dto.AccessTokenDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.Objects;

@Getter
public class UpdateUserPasswordRequestDto extends AccessTokenDto {

    @NotEmpty
    private final String currentPassword;
    @NotEmpty
    private final String newPassword;
    @NotEmpty
    private final String newPasswordCheck;

    public UpdateUserPasswordRequestDto(@NotEmpty String accessToken, String currentPassword, String newPassword, String newPasswordCheck) {
        super(accessToken);
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UpdateUserPasswordRequestDto that = (UpdateUserPasswordRequestDto) o;
        return Objects.equals(currentPassword, that.currentPassword) && Objects.equals(newPassword, that.newPassword) && Objects.equals(newPasswordCheck, that.newPasswordCheck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), currentPassword, newPassword, newPasswordCheck);
    }
}
