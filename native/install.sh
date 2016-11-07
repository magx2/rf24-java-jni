#!/bin/bash

# Taken from http://tmrh20.github.io/RF24Installer/RPi/install.sh

if [[ -z "$1" ]]; then
	echo "Please pass path where to work in first param"
	exit 101
fi

INSTALL_PATH="$1"
INSTALL_DIR="/rf24libs"

ROOT_PATH=${INSTALL_PATH}
ROOT_PATH+=${INSTALL_DIR}

echo "Installing ncurses library..."
sudo apt-get install libncurses5-dev

echo "Installing RF24 Repo..."
git clone https://github.com/tmrh20/RF24.git ${ROOT_PATH}/RF24
sudo make install -B -C ${ROOT_PATH}/RF24

echo "Installing RF24Network_DEV Repo..."
git clone https://github.com/tmrh20/RF24Network.git ${ROOT_PATH}/RF24Network
sudo make install -B -C ${ROOT_PATH}/RF24Network

echo "Installing RF24Mesh Repo..."
git clone https://github.com/tmrh20/RF24Mesh.git ${ROOT_PATH}/RF24Mesh
sudo make install -B -C ${ROOT_PATH}/RF24Mesh

echo "Installing RF24Gateway Repo..."
git clone https://github.com/tmrh20/RF24Gateway.git ${ROOT_PATH}/RF24Gateway
sudo make install -B -C ${ROOT_PATH}/RF24Gateway
