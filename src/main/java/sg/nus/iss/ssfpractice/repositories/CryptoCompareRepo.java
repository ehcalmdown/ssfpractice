package sg.nus.iss.ssfpractice.repositories;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository

public class CryptoCompareRepo {

    @Value("${crypto.cache.duration}")
    private Long cacheTime;

    @Autowired
    @Qualifier("redislab")//instansitates the redis template
    private RedisTemplate<String, String> redisTemplate; //might be String, Object or even Object, Object. please take note

    public void save(String coinName, String payload, String currency) {
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        valueOp.set(coinName.toLowerCase(), payload, Duration.ofMinutes(cacheTime));
    }

    public Optional<String> get(String coinName, String currency){
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        String value = valueOp.get(coinName.toLowerCase());
        if(null == value)
            return Optional.empty(); //empty case
        return Optional.of(value); //data contained
    }
    

}
