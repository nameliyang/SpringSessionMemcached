package org.springframework.beans.factory.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBeans {

	@Bean
	public MyBean myBean() {
		org.springframework.data.redis.listener.RedisMessageListenerContainer t;
		return  new MyBean();
	}
}
class MyBean{
	
}
