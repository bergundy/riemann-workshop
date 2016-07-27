import redis
import bernhard

r = redis.StrictRedis()
c = bernhard.Client()

c.send({
    'host': 'monitor',
    'service': 'queue gauge',
    'metric': r.llen('queue')
})
