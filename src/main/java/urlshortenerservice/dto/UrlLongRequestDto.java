package urlshortenerservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Builder
public class UrlLongRequestDto implements Serializable {

    private Long id;

    private String longUrl;

    private Date expiresDate;


}
