#!/bin/bash
set -ex
sudo apt-key update
sudo apt-get update
sudo apt-get upgrade -y
sudo apt-get install -y \
    wget \
    build-essential \
    git \
    curl \
    openjdk-7-jre \
    vim-nox python-pip \
    python-software-properties \
    software-properties-common

# Install tmux
sudo add-apt-repository -y ppa:pi-rho/dev
sudo apt-get update
sudo apt-get install -y tmux

# Install python packages
sudo pip install bernhard click flask tabulate

# Install redis
wget http://download.redis.io/redis-stable.tar.gz
tar xvzf redis-stable.tar.gz
cd redis-stable
make
sudo cp src/redis-cli src/redis-server /usr/local/bin
cd -
cat >> ~/.bashrc <<EOF
alias vi=vim
cd riemann-workshop
git pull
EOF

# Download and extract riemann
wget https://aphyr.com/riemann/riemann-0.2.11.tar.bz2
tar xvfj riemann-0.2.11.tar.bz2

# Init workshop
git clone https://github.com/bergundy/riemann-workshop
mv riemann-0.2.11/lib/riemann.jar riemann-workshop/resources
sudo wget -O /usr/local/bin/lein https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein
sudo chmod a+x /usr/local/bin/lein
cd riemann-workshop
lein test
cd -
