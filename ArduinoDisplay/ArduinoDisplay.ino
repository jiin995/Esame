#include <SPI.h>
#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

Adafruit_SSD1306 display;


void setup()
{
  Serial.begin(9600);
  display.begin(SSD1306_SWITCHCAPVCC, 0x3C);
  display.setTextSize(1);
  display.setTextColor(WHITE);
  display.clearDisplay();
  smile();
  display.display();

}
void loop()
{  
  String input = "";
  char character;

  while(Serial.available())
    {
        character = Serial.read();
        input.concat(character);
        delay(20);
  
    }
  if(input!="")
    {
      char com[3];
        input.toCharArray(com,3);
        if (com[0]=='/')
          {
              switch(com[1])
                {
                    case 'C':{ 
                                display.setCursor(0,0);
                                display.clearDisplay();
				                        display.display();
                                break;
                            }
                    case 'w':{
                                printtext(input);
                                break;
                              }
                    case 'L':{
                                 display.clearDisplay();
                                 smile();
                                 display.display();
                                   break;
                              }
                  
                }
           }

    }
}
