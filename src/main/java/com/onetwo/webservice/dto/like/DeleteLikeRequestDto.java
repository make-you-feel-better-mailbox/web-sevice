package com.onetwo.webservice.dto.like;

import com.onetwo.webservice.dto.AccessTokenDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Objects;

@Getter
public class DeleteLikeRequestDto extends AccessTokenDto {

    @NotNull
    private final Integer category;

    @NotNull
    private final Long targetId;

    public DeleteLikeRequestDto(@NotEmpty String accessToken, Integer category, Long targetId) {
        super(accessToken);
        this.category = category;
        this.targetId = targetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DeleteLikeRequestDto that = (DeleteLikeRequestDto) o;
        return Objects.equals(category, that.category) && Objects.equals(targetId, that.targetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category, targetId);
    }
}
