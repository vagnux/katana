#!/bin/bash
mkdir /grallvm
cd /grallvm
chmod +x /spring/jdk
/spring/jdk
rm -r /usr/lib/jvm
mv graalvm* /usr/lib/jvm

