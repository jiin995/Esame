void arduinologo()
  {
      display.setCursor(0,0);
      display.clearDisplay();
      for (int i=0;i<3;i++)
        {
            display.drawCircle(30,14,14-i,WHITE);
            display.drawCircle(60,14,14-i,WHITE);
            display.drawLine(24,16-i,36,16-i,WHITE);
            display.drawLine(54,16-i,66,16-i,WHITE);
            display.drawLine(61-i,10,61-i,21,WHITE);
        }
      display.setCursor(80,15);
        printtext("Arduino")
  }

void printtext (String input)
  {
  		char text[100];
      	if(input!="")
        	{
            	input.toCharArray(text,input.length()+1);
            	int dim=input.length()+1;
              	for(int i=2;i<dim;i++)
                	{      
                    	  display.write(text[i]);  
                    	  display.display();
             
            	    }
          	}
  }

void smile()
  {
  		display.clearDisplay();
    	occhio(5,15);
    	occhio(5,100);
    	for (int i=0;i<6;i++)
    		{  
      			display.drawLine(30,33+i,35,33+i,WHITE);
      			display.drawLine(95,33+i,90,33+i,WHITE);
      			display.drawLine(35,45+i,90,45+i,WHITE);
      			display.drawLine(35+i,44,35+i,33,WHITE);
      			display.drawLine(90-i,44,90-i,33,WHITE);
    		}
  }

void occhio(int x,int y)
  {
  		for (int i=0;i<11;i++)
    		for(int j=0;j<6;j++)
      			display.drawPixel(y+i,x+j,WHITE);
  }
