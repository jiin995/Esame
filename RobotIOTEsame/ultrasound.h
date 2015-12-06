/*************************************
          Definition
          of library ultrasound.h
 *************************************/

 /*********************************************************************
    This library was created for controll ultrasound of robot.
    In particular the library work for HC-SR04 ultrasonic sensors
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
