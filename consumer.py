import redis

r = redis.StrictRedis()

while True:
    task = r.blpop('queue')
    print task
