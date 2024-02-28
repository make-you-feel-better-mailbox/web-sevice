package com.onetwo.webservice.dto.comment;

import com.onetwo.webservice.dto.AccessTokenDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Objects;

@Getter
public class RegisterCommentRequestDto extends AccessTokenDto {

    @NotNull
    private final Integer category;

    @NotNull
    private final Long targetId;

    @NotNull
    private final String content;

    public RegisterCommentRequestDto(@NotEmpty String accessToken, Integer category, Long targetId, String content) {
        super(accessToken);
        this.category = category;
        this.targetId = targetId;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RegisterCommentRequestDto that = (RegisterCommentRequestDto) o;
        return Objects.equals(category, that.category) && Objects.equals(targetId, that.targetId) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category, targetId, content);
    }
}
