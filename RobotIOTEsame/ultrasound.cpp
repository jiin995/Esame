/*************************************
          Implementation
          of library ultrasound.h
 *************************************/

#include "ultrasound.h"

namespace arduino_web_car
{

  ultrasound::ultrasound(int eP,int tP)

    {
        if(eP!=tP)
          {
              echoPort=eP;
              triggerPort=tP;
          }
    }

    int ultrasound::getDistance()
      {
          digitalWrite( triggerPort, LOW );
          digitalWrite( triggerPort, HIGH );
          delayMicroseconds( 20 );
          digitalWrite( triggerPort, LOW );

          long durata = pulseIn( echoPort, HIGH );
          long r = 0.034 * durata / 2;

          if( durata > 38000 ) //Distanza fuori dalla rilevazione
              return -1;
          else
              return r ;
      }
}
