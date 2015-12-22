/*************************************
          Definition
          of the robot's display using
          adafruit technies
 *************************************/

void checkcommand(char *input)
  {
      String c=input;
      if(c=="/w/C")
        displaySerial.println("/C");
      else 
        if(c=="/w/L")
          displaySerial.println("/L");
        else
          if(c=="/w/S")
            displaySerial.println("/S");
           else
            if(c=="/w/T")
              displaySerial.println("/T");
            else
             if(c=="/w/t")
              displaySerial.println("/t");
             else  
              displaySerial.println(input);
            
  }
