package com.onetwo.webservice.dto.comment;

import com.onetwo.webservice.dto.AccessTokenDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.Objects;

@Getter
public class UpdateCommentRequestDto extends AccessTokenDto {

    @NotEmpty
    private final String content;

    public UpdateCommentRequestDto(@NotEmpty String accessToken, String content) {
        super(accessToken);
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UpdateCommentRequestDto that = (UpdateCommentRequestDto) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), content);
    }
}
