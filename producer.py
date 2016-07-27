import redis
import time

r = redis.StrictRedis()

while True:
    r.rpush('queue', time.time())
    time.sleep(0.1)
