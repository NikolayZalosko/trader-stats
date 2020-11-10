package com.nickz.traderstats.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedisKeyExpiredEventListener extends KeyExpirationEventMessageListener {
    
    public RedisKeyExpiredEventListener(RedisMessageListenerContainer listenerContainer) {
	super(listenerContainer);
    }
    
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.debug("Key expired");
    }

}
