# Facial Detection and Tracking

This code was created for HackISU October 2018 over the course of 36 hours from scratch. It uses OpenCV to detect a face, and an Arduino board to drive motors to move a camera to follow the face as it moves around.

[DevPost](https://devpost.com/software/facial-recognition-and-tracking)  
[Demo Video](https://www.youtube.com/watch?v=eH3_yiz7nn4)

## Authors

#### *Created By:*
[Daniel Barnes](https://github.com/danielbarnes175)  
[Ryan Spitz](https://github.com/RyanSpitz)  
[Thaddeus Hill](https://github.com/ThaddeusRHill)  
[Jacob Dostal](https://github.com/JayDostal)  

## About

#### Parts:
1. Webcam
2. Arduino board
3. Java program
4. 2 Motors

#### Features:
The features of the device are to track a person's face and move the camera such that it follows them, assuming they don't move too quickly out of frame.

#### Libraries used:
**OpenCV** - Used for facial recognition  
**JavaFX SDK** - Used for displaying camera output / UI  
**RXTX** - Used for serializing data to send to the Arduino
