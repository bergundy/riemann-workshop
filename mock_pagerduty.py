import tabulate
import time
import click
import logging

from flask import Flask, request
from threading import Thread
from collections import defaultdict

app = Flask(__name__)

incidents = {}

V = click.style(u'\u2713', fg='green')
X = click.style('X', fg='red')
U = click.style('?', fg='yellow')

action_to_state = defaultdict(lambda: U, {
    'trigger': X,
    'resolve': V
})


@app.route('/incidents/<action>', methods=['POST'])
def handle_incident(action):
    incident = request.get_json()
    incident['state'] = action_to_state[action]
    key = incident['key']
    transformed = incident['details']
    transformed.update(incident)
    incidents[key] = transformed
    return 'OK'


def select_keys(*keys):
    return {k: dct[k] for k in keys if k in dct}


def juxt(*keys):
    return lambda dct: [dct.get(k, None) for k in keys]


def print_status_loop():
    while True:
        click.clear()
        colums = 'header', 'state', 'description'
        rows = map(juxt(*colums), incidents.values())
        click.echo(tabulate.tabulate(rows, headers=colums))
        time.sleep(1)


if __name__ == '__main__':
    logging.basicConfig(level=logging.ERROR)
    t = Thread(target=print_status_loop, args=())
    t.daemon = True
    t.start()
    app.run()
