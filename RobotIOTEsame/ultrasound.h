/*************************************
          Definition
          of library ultrasound.h
 *************************************/

 /*********************************************************************
    This library was created for checking the ultrasounds that the robot could recive.
    Particularly the library works with HC-SR04 ultrasonic sensors
    or similiary
 ***********************************************************************/

#include "Arduino.h"
#ifndef ULTRASOUND
#define ULTRASOUND

namespace arduino_web_car
  {
      class ultrasound{
          private:
                //CONTROL PINS
                  int echoPort;
                  int triggerPort;
          public:
                  ultrasound(int eP,int tP);
                  int getDistance();
      };
    }

#endif /* ULTRASOUND */
