#include <Keyboard.h>

#include <Servo.h>
  
  Servo panServo;
  Servo tiltServo;
  
void setup() {
  // put your setup code here, to run once:
  panServo.attach(A5);
  tiltServo.attach(A0); 
  Serial.begin(9600);
  panServo.write(0);
  tiltServo.write(0);
  delay(2000);
}

void loop() {
  // put your main code here, to run repeatedly:
    panServo.write(0);
    tiltServo.write(0);
    delay(1000);
  for(int i = 0;i <= 180; i= i+5){
    panServo.write(i);
    tiltServo.write(i);
    delay(50);
  }
  delay(1000);
  for(int i = 180;i >= 0; i= i-5){
    panServo.write(i);
    tiltServo.write(i);
    delay(50);
  }
}
