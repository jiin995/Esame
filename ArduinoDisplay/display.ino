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
        display.write('A');
        display.write('r');
        display.write('d');
        display.write('u');
        display.write('i');
        display.write('n');
        display.write('o');
        display.display();
}

void printtext (String input)
{
  display.clearDisplay();
  display.setCursor(0,0);
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
    for (int i=0;i<4;i++)
    {  
      display.drawLine(30,21+i,35,21+i,WHITE);
      display.drawLine(95,21+i,90,21+i,WHITE);
      display.drawLine(35,26+i,90,26+i,WHITE);
      display.drawLine(35+i,22,35+i,27,WHITE);
      display.drawLine(90-i,22,90-i,27,WHITE);
    }
}

void occhio(int x,int y)
{
  for (int i=0;i<11;i++)
    for(int j=0;j<6;j++)
      display.drawPixel(y+i,x+j,WHITE);
}

