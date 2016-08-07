#!/usr/bin/env python
from __future__ import print_function
import bernhard
import socket
import sys

to_dict = lambda s: dict(x.split(':') for x in s.split())


def write(header, data=None):
    if data:
        sys.stdout.write('{} {}\n{}'.format(header, len(data), data))
    else:
        sys.stdout.write('{}\n'.format(header))
    sys.stdout.flush()


def reader():
    write('READY')
    line = sys.stdin.readline()
    if not line:
        return None
    headers = to_dict(line)
    data = sys.stdin.read(int(headers['len']))
    return headers, to_dict(data)


def main():
    host = socket.gethostbyname(socket.getfqdn())
    riemann = bernhard.Client()

    for headers, data in iter(reader, None):
        if headers['eventname'].startswith('PROCESS_STATE_'):
            proc_name = data['processname']
            group_name = data['groupname']
            from_state = data['from_state']
            to_state = headers['eventname'].replace('PROCESS_STATE_', '')
            service = 'snitch {}:{} state'.format(group_name, proc_name)
            riemann.send({'host': host, 'service': service, 'state': to_state, 'tags': ['snitch'], 'attributes': data})
        write('RESULT', 'OK')


if __name__ == '__main__':
    main()
