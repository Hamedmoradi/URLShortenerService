package urlshortenerservice.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

class UrlLongRequestDtoTest {

    private UrlLongRequestDto urlLongRequestDtoUnderTest;

    @BeforeEach
    void setUp() {
        urlLongRequestDtoUnderTest = new UrlLongRequestDto(15L, "http://google.com",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    }

    @Test
    void testBuilder() {
        final UrlLongRequestDto.UrlLongRequestDtoBuilder result = UrlLongRequestDto.builder();
    }
}
