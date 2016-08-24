#!/usr/bin/env bash

java -Djava.library.path=/Users/hannesmoser/Documents/projects/fh/impulse/face-recognition/lib \
     -classpath /Users/hannesmoser/Documents/projects/fh/impulse/face-recognition/bin/opencv-2413.jar:/Users/hannesmoser/Documents/projects/fh/impulse/face-recognition/build/jar/FaceRecognition.jar \
     FaceRecognition \
     | ffmpeg \
        -re \
        -i pipe:0 \
        -an \
        -f flv \
        udp://127.0.0.1:8090/cam.ffm
