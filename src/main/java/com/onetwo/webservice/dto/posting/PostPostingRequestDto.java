package com.onetwo.webservice.dto.posting;

import com.onetwo.webservice.dto.AccessTokenDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.Objects;

@Getter
public class PostPostingRequestDto extends AccessTokenDto {

    @NotEmpty
    private final String content;

    private final Boolean mediaExist;

    public PostPostingRequestDto(String accessToken, String content, Boolean mediaExist) {
        super(accessToken);
        this.content = content;
        this.mediaExist = mediaExist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PostPostingRequestDto that = (PostPostingRequestDto) o;

        if (!Objects.equals(content, that.content)) return false;
        return Objects.equals(mediaExist, that.mediaExist);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (mediaExist != null ? mediaExist.hashCode() : 0);
        return result;
    }
}
