/*************************************
          Definition
          of MAIN
 *************************************/
 
#include "define.h"
#include "Motor.h"
#include "ultrasound.h"
#include "espmqtt.h"
#include <dht11.h>

using namespace arduino_web_car;

espmqtt espm(rxEsp,txEsp);
motor motorfr (DIR_A_FR,DIR_B_FR,PWM_FR);
motor motorfl (DIR_A_FL,DIR_B_FL,PWM_FL);
motor motorbl (DIR_A_BL,DIR_B_BL,PWM_BL);
motor motorbr (DIR_A_BR,DIR_B_BR,PWM_BR);

ultrasound ultrasoundF(ECHO_F,TRIG_F);
ultrasound ultrasoundB(ECHO_B,TRIG_B);

bool ledstate=false;

dht11 DHT;

int globalGas=50;

//Object initialization to communicate with the ArduinoDisplay
SoftwareSerial displaySerial =  SoftwareSerial(rxPin, txPin);

void setup()
  {
      
      Serial.begin(9600);
      pinSetup();
      displaySerial.begin(9600);
      displaySerial.println("/C");
      delay(100);
      displaySerial.println("/L");
      espm.wifiConnect();
        delay(1000);
      espm.connectMqtt();
  }

void loop()
  {
      if(Serial.available())
        {
            char c=Serial.read();
              if(c=='M')
                menu();
        }
      String cm=espm.recive();
        if(cm!="0")
          Serial.println(cm[0]);//Debug Print
      char command=cm[0];
      switch(command)
        {
           case 'T':{
                        delay(100);
                        char *d=convertmessage(cm);
                        checkcommand(d);
                        Serial.println(d);
                        //displaySerial.println(d);
                        break;
                     }

           case 'A':{
                        go_forwards(motorfl,motorfr,motorbr,motorbl);
                        break;
                     }

           case 'I':{
                        go_backwards(motorfl,motorfr,motorbr,motorbl);
                        break;
                     }

           case 'S':{
                        go_left(motorfl,motorfr,motorbl,motorbr);
                        break;
                     }

           case 'D':{
                        go_right(motorfl,motorfr,motorbl,motorbr);
                        break;
                     }

           case 's':{
                         stop(motorfl,motorfr,motorbr,motorbl);
                         break;
                     }

           case 'G':{     
                         if((globalGas<MAXGAS)&&(globalGas>MINGAS))
                            globalGas+=10;
                         set_gas(motorfl,motorfr,motorbr,motorbl,globalGas);
                         break;
                     }

           case 'g':{ 
                         if((globalGas<MAXGAS)&&(globalGas>MINGAS))
                            globalGas-=10;
                         set_gas(motorfl,motorfr,motorbr,motorbl,globalGas);
                         break;
                     }
           case 'r':{
                        int front=ultrasoundF.getDistance();
                        int back=ultrasoundB.getDistance();
                          DHT.read(DHT11_PIN);
                        int temp=DHT.temperature;
                        Serial.println(temp);
                          espm.publish("sensorfront",String(front));
                          espm.publish("sensorback","ciao");
                          espm.publish("temp",String(temp));
                        Serial.println(ultrasoundF.getDistance());
                        break;
                     }
           case 'l':{  
                      
                        if(ledstate)
                          {
                            digitalWrite(LED,LOW);
                            ledstate=false;
                          }
                        else 
                          {
                            ledstate=true;
                            digitalWrite(LED,HIGH);
                          }
                         break;              
                      }
              
        }
      delay(50);
  }
