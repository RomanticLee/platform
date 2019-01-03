package cn.newcapec.city.smart.gateway.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * 测试MQ消费者
 * Created by es on 2018/12/24.
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "mytopic1", consumerGroup = "tag1")
public class MyConsumer1 implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        //接收到了MQ的消息
        log.info("received message: {}", message);
    }
}
