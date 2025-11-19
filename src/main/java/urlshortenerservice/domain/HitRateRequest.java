package urlshortenerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.*;

@Entity(name = "HIT_RATE_REQUEST")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class HitRateRequest {

    @Id
    private Long id;

    private Long hitRate;

    private String shorterUrl;

}
