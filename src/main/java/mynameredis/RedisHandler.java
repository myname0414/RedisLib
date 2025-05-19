package mynameredis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.UnifiedJedis;

public class RedisHandler {

    protected UnifiedJedis publisherJedis;
    protected UnifiedJedis subscriberJedis;

    protected Thread subscriberThread;

    public RedisHandler(String host, int port) {
        HostAndPort node = new HostAndPort(host, port);
        publisherJedis = new UnifiedJedis(node);
        subscriberJedis = new UnifiedJedis(node);
    }

    public void subscribe(JedisPubSub subscriber, String[] channels) {
        subscriberThread = new Thread(() -> {
            subscriberJedis.subscribe(subscriber, channels);
        }, "RedisSubThread");
        subscriberThread.start();
    }

    public void sendMessage(String channle, String message) {
        publisherJedis.publish(channle, message);
    }

}
