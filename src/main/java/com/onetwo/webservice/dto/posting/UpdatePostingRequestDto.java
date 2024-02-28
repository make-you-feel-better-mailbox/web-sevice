package com.onetwo.webservice.dto.posting;

import com.onetwo.webservice.dto.AccessTokenDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.Objects;

@Getter
public class UpdatePostingRequestDto extends AccessTokenDto {

    @NotEmpty
    private final String content;

    private final Boolean mediaExist;

    public UpdatePostingRequestDto(@NotEmpty String accessToken, String content, Boolean mediaExist) {
        super(accessToken);
        this.content = content;
        this.mediaExist = mediaExist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UpdatePostingRequestDto that = (UpdatePostingRequestDto) o;
        return Objects.equals(content, that.content) && Objects.equals(mediaExist, that.mediaExist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), content, mediaExist);
    }
}
