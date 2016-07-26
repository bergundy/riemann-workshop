import time
import bernhard
from contextlib import contextmanager
from functools import partial
from socket import gethostname

hostname = gethostname()
riemann = bernhard.Client()


@contextmanager
def monitor(service, attributes=None, success='passed', failed='failed'):
    start_time = time.time()
    try:
        yield
        details = ''
        state = success
    except Exception as e:
        details = str(e)
        state = failed
        raise
    finally:
        duration = int((time.time() - start_time) * 1000)
        riemann.send({
            'host': hostname,
            'service': service,
            'metric': duration,
            'state': state,
            'attributes': attributes or {},
            'description': details
        })


hit_miss_monitor = partial(monitor, success='hit', failed='miss')
