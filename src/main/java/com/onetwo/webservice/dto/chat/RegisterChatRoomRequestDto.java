package com.onetwo.webservice.dto.chat;

import com.onetwo.webservice.dto.AccessTokenDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class RegisterChatRoomRequestDto extends AccessTokenDto {

    private final List<String> targetUserIds;

    public RegisterChatRoomRequestDto(@NotEmpty String accessToken, List<String> targetUserIds) {
        super(accessToken);
        this.targetUserIds = targetUserIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RegisterChatRoomRequestDto that = (RegisterChatRoomRequestDto) o;
        return Objects.equals(targetUserIds, that.targetUserIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), targetUserIds);
    }

    @Override
    public String toString() {
        return "RegisterChatRoomRequestDto{" +
                "targetUserIds=" + targetUserIds +
                '}';
    }
}
