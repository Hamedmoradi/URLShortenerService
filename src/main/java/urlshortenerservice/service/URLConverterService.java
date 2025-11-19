package urlshortenerservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import urlshortenerservice.domain.ShortenRequest;
import urlshortenerservice.dto.UrlLongRequestDto;
import urlshortenerservice.exceptions.InvalidUrlException;
import urlshortenerservice.mapper.ShortenRequestToUrlLongRequestDtoMapper;
import urlshortenerservice.repository.ShortenRequestRepository;
import urlshortenerservice.utilities.URLConvertor;
import urlshortenerservice.utilities.URLValidator;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
@AllArgsConstructor
@Slf4j
public class URLConverterService {

    private final RedisTemplate redisTemplate;
    private final ShortenRequestToUrlLongRequestDtoMapper mapper;
    private final ShortenRequestRepository shortenRequestRepository;



    public String convertToShortUrl(UrlLongRequestDto request) {
        log.info("Received url to shorten: {}", request.getLongUrl());
        if (URLValidator.INSTANCE.validateURL(request.getLongUrl())) {
        var url = new ShortenRequest();
        url.setLongUrl(request.getLongUrl());
        url.setExpiresDate(request.getExpiresDate());
        url.setCreatedDate(new Date());
        var entity = shortenRequestRepository.save(url);
        redisTemplate.opsForValue().set(entity.getId().toString(), entity, 5, TimeUnit.SECONDS);
        return URLConvertor.createUniqueID(entity.getId());
        }
        throw new InvalidUrlException("Please enter a valid URL");
    }

    public @ResponseBody UrlLongRequestDto getOriginalUrl(String shortUrl) {
        var id = URLConvertor.getDictionaryKeyFromUniqueID(shortUrl);
        var entity = shortenRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entity with " + shortUrl));

        if (entity.getExpiresDate() != null && entity.getExpiresDate().before(new Date())) {
            throw new EntityNotFoundException("Link expired!");
        }
        return mapper.destinationToSource(entity);

    }

}
