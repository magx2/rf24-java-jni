[![Build Status](https://travis-ci.org/magx2/rf24-java-jni.svg?branch=master)](https://travis-ci.org/magx2/rf24-java-jni)

# Installing into your java project

## Get 
**TODO**

# Running tests

Warning: *Remember you need to have ```sudo``` right to run examples!*

## Ping pong (checking round trip time)

In this 2 examples you can run code on 2 different devices (i.e. Java + Java or Java + Arduino) and check what is the round trip time.

### Server

To run it type (in main dir):

    ./gradlew :examples:runRf24PingPongServerExample
    
_Note: you can run Rf24PingPongClientExample.ino on Arduino to have second device that will answer calls._

### Client

To run it type (in main dir):

    ./gradlew :examples:runRf24PingPongClientExample

_Note: you can run Rf24PingPongServerExample.ino on Arduino to have second device that will start pinging this example._

# RF24 

## Prerequisites

Note: *You need those programs on machine that you want to compile 
library, i.e. Raspberry PI*

```bash
$ git --version
git version 2.7.4

$ gcc --version
gcc (Ubuntu 5.4.0-6ubuntu1~16.04.2) 5.4.0 20160609
Copyright (C) 2015 Free Software Foundation, Inc.
This is free software; see the source for copying conditions.  There is NO
warranty; not even for MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

$ g++ --version
g++ (Ubuntu 5.4.0-6ubuntu1~16.04.2) 5.4.0 20160609
Copyright (C) 2015 Free Software Foundation, Inc.
This is free software; see the source for copying conditions.  There is NO
warranty; not even for MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
```

Install everything with:

```bash
sudo apt-get install git gcc g++
```

Also remember to update your system:

```bash
sudo apt-get update
sudo apt-get dist-upgrade
```

### Enable SPI on Raspberry PI

Open RPi config app ```sudo raspi-config```, select Advanced and enable 
the SPI kernel module. ```sudo reboot```

### Clone repository

```bash
git clone https://github.com/magx2/rf24-java-jni.git
cd rf24-java-jni
```

## RF24 native libs

Native libraries is taken from this repository: https://github.com/TMRh20/RF24/
(checked with commit SHA **c94f87746552a3c662c35fd11e772b747f19d8ee**). 
This repository is in folder *./native/RF24* as a ```git submodule```. To download
it run:

```bash
git submodule init
git submodule update
ls ./native/RF24
```

## Compiling native library

Note: *In ./native/precompiled/ folder you can fins precomputed libraries - 
there is no need to compile it by hand!*

On you RPi just run (remember you need to have ```sudo``` rights!):

```gradle
./gradlew clean compileRf24
```

If everything went smooth you should find your native library in *./build/wrapper/librf24bcmjava.so*.
Use this library with your app. Remember to load it in your java code before using any RF24 related code:

```java
public class Main {
    static {
        try {
            System.loadLibrary(RF24_LIB_NAME);
        } catch (UnsatisfiedLinkError e) {
            // handel this if you want
        }
    }

    public static void main(String[] args) {
        // ... your staff ...
    }
}
```

If you will be using my ```RF24Adapter``` use provided method to do this 
less verbose: ```RF24Adapter.loadLibrary()```.

### Configuring your build

There are some properties that you can configure. All defaults runs perfectly fine
on Raspberry PI 3 with Raspbian.

Variable                | Description                     | Default
------------------------|---------------------------------|---------
javaJni                 | Points where headers fo JNI are | /usr/lib/jvm/jdk-8-oracle-arm32-vfp-hflt/include
javaJniPlatformSpecific | Points where platform specific headers fo JNI are. | $javaJni/linux
headersDir              | Remote header files target installation directory. Run ```./native/RF24/configure --help``` for more information | RPi
wrapperBuildDir         | Where to build wrapper           | $project.buildDir/wrapper

To set those properties you can run with ```-P<var>=<new_value>``` or
 set it in *~/.gradle/gradle.properties* file.

# SWIG

Note: *This step is not mandatory. All artefacts of SWIG compilation are commited
into repository (./swig-config/rf24bcmjava_wrap.cxx and generated classes in
./src/main/java/pl/grzeslowski/smarthome/rf24/generated).*

## Install SWIG

Download SWIG from http://www.swig.org/download.html (tested on version 3.0.10)
and install it. In downloaded zip you should find file called *INSTALL*. 
Follow this instructions.

Note: *Remember to add swig folder into your PATH (edit ~/.bashrc file on 
Linux)*

Check if your installation has succed:

```bash
$ swig -version
SWIG Version 3.0.10
Compiled with g++ [x86_64-pc-linux-gnu]
Configured options: +pcre
```

## Generate SWIG files

First run Gradle task to clean SWIG, ```removeSwigGeneratedFiles``` and then
run code generation ```compileSwig``` 

```gradle
./gardlew removeSwigGeneratedFiles compileSwig
```

Note: *If you want to commit those newly generated files, just fork my repository.
Also think if it would be good to make pull request back to me :).*

# Tested on

## Compiling RF24

Raspberry PI 3 with Raspbian

```bash
$ uname -a
Linux raspberrypi 4.4.21-v7+ #911 SMP Thu Sep 15 14:22:38 BST 2016 armv7l GNU/Linux
```

## Compiling SWIG

PC with Ubuntu:

```bash
$ uname -a
Linux xxx 4.4.0-45-generic #66-Ubuntu SMP Wed Oct 19 14:12:37 UTC 2016 x86_64 x86_64 x86_64 GNU/Linux
```
