#include <SPI.h>
#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

Adafruit_SSD1306 display;
int sizetext=1;

void setup()
 {
  		Serial.begin(9600);
  		display.begin(SSD1306_SWITCHCAPVCC, 0x3C);
  		display.setTextSize(sizetext);
  		display.setTextColor(WHITE);
  		display.clearDisplay();
  		smile();
  		display.display();
 }
 
void loop()
 {
  		String input = "";
  		char character;
  		
  		while(Serial.available()) 				// Leggo dalla seriale
    		{
        		character = Serial.read();
        		input.concat(character);
        		delay(20);     					// Aspetto 20ms per evitare problemi di sovrapposizione delle informazioni
    		}
  		if(input!="")
    		{
      			char com[3];
        			input.toCharArray(com,3);
        		if (com[0]=='/') 				// Controllo che mi sia arrivato un comando e agisco di conseguenza
          			{
              			switch(com[1])
                			{
                				// Pulizia Display
                    			case 'C':{
                                    		display.setCursor(0,0);
                                    		display.clearDisplay();
				    						display.display();
                                    		break;
                            			}
                            			
                    			// Visualizzazione messaggio
                    			case 'w':{
                                    		display.clearDisplay();
                                    		display.setCursor(0,0);
                                    		printtext(input);
                                    		break;
                              			}
                              			
                    			// Visualizzazione Smile
                    			case 'S':{
                                 		   	display.clearDisplay();
                                  		 	smile();
                                    		display.display();
                                   			break;
                       			       }
                       			       
		                    	// Visualizzazione Logo Arduino
                    			case 'L':{
                                    		display.clearDisplay();
                                    		arduinologo();
                                    		display.display();
                                    		break;
                            			}
                            			
                    			// Aumento dimensione testo
                    			case 'T':{
		                                    sizetext++;
                                 		   	display.setTextSize(sizetext);
                                    		break;
                            			}
                            			
                    			// Diminuzione dimensione testo
                    			case 't':{
                                    		sizetext++;
		                                    display.setTextSize(sizetext);
        		                            break;
                			            }
                			            
                    			// Visualizzazione Ringraziamenti        
                    			case'R':{
                                    		display.clearDisplay();
                                    		display.setCursor(0,0);
		                                    printtext("/wSviluppato da:       Gabriele Previtera,");
		                                    printtext("/w Saverio Milo ,      ");
        		                            printtext("/wMichele Pommella,    Alessandro Pozziello,");
                		                    printtext("/w Con l'aiuto di          Paolo Maresca");
                        		            break;
                            			}
                	 }// End Case
           	 }// End if command
    	 }//End if input
 }//End loop