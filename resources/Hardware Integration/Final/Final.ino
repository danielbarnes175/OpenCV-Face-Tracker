
#include <Keyboard.h>
#include <Servo.h>

Servo panServo;
Servo tiltServo;
double pS = 90;
double tS = 90;
double angleChange = 0.5;

void setup() {
  // put your setup code here, to run once:
  panServo.attach(A5);
  tiltServo.attach(A0);
  Serial.begin(9600);
  tiltServo.write(90);
  panServo.write(65);
}

void loop() {
  if (Serial.available() > 0) { //read incoming serial data
    char inChar = Serial.read();

    //Tilts up
    if (inChar == 'd') {
      tS = tS + angleChange;
      Serial.println(tS);
      if (tS < 145) {
        tiltServo.write(tS);
      }
      else {
        // this is out of the servo bounds.
        tS = 105;
      }
    }

    //Tilts down
    else if (inChar == 'u') {
      tS = tS - angleChange;

      if (tS > 40) {
        tiltServo.write(tS);
      }
      else {
        //this is out of servo bound
        tS = 70;
      }
    }
    //Pan Right
    else if (inChar == 'r') {
      pS = pS - angleChange;

      if (pS > 25) {
        panServo.write(pS);
      }
      else {
        //this is out of servo bound
        pS = 25;
      }
    }
    //Pan Left
    else if (inChar == 'l') {
      pS = pS + angleChange;

      if (pS < 105) {
        panServo.write(pS);
      }
      else {
        // this is out of the servo bounds.
        pS = 105;
      }
    }
    else if (inChar == 'N') {
      //do nothing
    }
  }
}
