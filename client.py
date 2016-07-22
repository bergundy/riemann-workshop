from bernhard import Client

c = Client()
c.send({
    'host': 'localhost',
    'service': 'workshop startup notifier',
    'state': 'success',
    'tags': ['info'],
    'attributes': {'clojure': 'cool'}
})
