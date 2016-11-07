#!/bin/bash

INSTALL_PATH="."
INSTALL_DIR="/rf24libs"

ROOT_PATH=${INSTALL_PATH}
ROOT_PATH+=${INSTALL_DIR}

echo "Install ncurses library, recommended for RF24Gateway [Y/n]? "
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
make -B -C${ROOT_PATH}/RF24Gateway/examples/ncurses; echo ""; echo "Complete, to run the example, cd to rf24libs/RF24Gateway/examples/ncurses and enter  sudo ./RF24Gateway_ncurses";;
