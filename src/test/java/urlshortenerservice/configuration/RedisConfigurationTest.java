package urlshortenerservice.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
public class RedisConfigurationTest {

    @Test
    public void contextLoads() {
    }


    @Configuration
    static class Config {
        @Bean
        @SuppressWarnings("unchecked")
        public RedisSerializer<Object> defaultRedisSerializer() {
            return Mockito.mock(RedisSerializer.class);
        }


        @Bean
        public RedisConnectionFactory connectionFactory() {
            RedisConnectionFactory factory = Mockito.mock(RedisConnectionFactory.class);
            RedisConnection connection = Mockito.mock(RedisConnection.class);
            Mockito.when(factory.getConnection()).thenReturn(connection);

            return factory;
        }
    }
}