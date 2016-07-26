import click
import pipes

from monitor import monitor
from subprocess import check_call


@click.command()
@click.argument('command', nargs=-1)
def main(command):
    service = ' '.join(map(pipes.quote, command))
    with monitor(service):
        check_call(command)

if __name__ == '__main__':
    main()
