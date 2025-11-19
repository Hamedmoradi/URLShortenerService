package urlshortenerservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity(name = "SHORTEN_REQUEST_URL")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ShortenRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String longUrl;

    @Column(nullable = false)
    private Date createdDate;
    @Column(nullable = false)
    private Date expiresDate;


    @JsonCreator
    public ShortenRequest() {

    }
}

