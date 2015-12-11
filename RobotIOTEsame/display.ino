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
            displaySerial.println(input);
  }

