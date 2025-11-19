package urlshortenerservice.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import urlshortenerservice.domain.HitRateRequest;
import urlshortenerservice.domain.ShortenRequest;
import urlshortenerservice.dto.UrlLongRequestDto;
import urlshortenerservice.repository.HitRateRequestRepository;
import urlshortenerservice.service.URLConverterService;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/snapptrip/hub/")
@AllArgsConstructor
public class URLController {

    private final RedisTemplate redisTemplate;
    private final URLConverterService urlConverterService;
    private final HitRateRequestRepository hitRateRequestRepository;

    @PostMapping(value = "/create-shortener-url")
    public String shortenUrl(@RequestBody @Valid final ShortenRequest shortenRequest) {
            return urlConverterService.convertToShortUrl(
                    UrlLongRequestDto
                            .builder()
                            .longUrl(shortenRequest.getLongUrl())
                            .expiresDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2)))
                            .build());
    }


//    @ApiOperation(value = "Redirect", notes = "Finds original url from short url and redirects")
    @CachePut(value = "hitRateRequests", key = "#p0")
    @GetMapping(value = "{shortUrl}")
    public String getAndRedirect(@PathVariable String shortUrl) {
        var url = urlConverterService.getOriginalUrl(shortUrl);
        if (url != null) {
            log.info("URL is {}", url);
            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
            Long rate = valueOps.increment(url.getLongUrl());
            log.info("THE RATE OF  {} (SHORT-URL) IS : {}", shortUrl, rate);
            hitRateRequestRepository.save(HitRateRequest.builder().hitRate(rate).shorterUrl(shortUrl).id(url.getId()).build());
            return "redirect to " + url.getLongUrl() + " successful";

        }
        return "url not found";
    }

    @GetMapping(value = "/hit-rate/{shortUrl}")
    public Long getUrlHitRate(@PathVariable String shortUrl) {
        return hitRateRequestRepository.findByShorterUrl(shortUrl).getHitRate();
    }
}



