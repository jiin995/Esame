/*************************************
          Definition
          of pins for I/O operation
*************************************/
#ifndef DEFINE_H
#define DEFINE_H

#include "Arduino.h"

//Connessione secondo arduino
#define rxPin 15      //txpin 0 altro arduino
#define txPin 14      //rxpin 1 altro arduino
//Connessione ESP8266
#define rxEsp 52      //rxpin per connettere l'esp8622
#define txEsp 53      //txpin per connettere l'esp8622

//Pins per motor drive
#define DIR_A_FR 23   //Motore frontale destro
#define DIR_B_FR 22
#define PWM_FR 13
#define DIR_A_FL 25   //Motore frontale sinistro
#define DIR_B_FL 24
#define PWM_FL 12
#define DIR_A_BL 26   //Motore posteriore sinistro
#define DIR_B_BL 27
#define PWM_BL 11
#define DIR_A_BR 28   //Motore posteriore destro
#define DIR_B_BR 29
#define PWM_BR 10

//Pin per Ultrasuoni
#define ECHO_F A0     //Ultrasuoni frontale
#define TRIG_F 30
#define ECHO_B A1     //Ultrasuoni posteriore
#define TRIG_B 32
#define DHT11_PIN 31
#define LED 32

//Pin Settings

void pinSetup()
{
     pinMode(rxPin, INPUT);
     pinMode(txPin, OUTPUT);
     pinMode(DIR_A_FR,OUTPUT);
     pinMode(DIR_B_FR,OUTPUT);
     pinMode(PWM_FR,OUTPUT);
     pinMode(DIR_A_FL,OUTPUT);
     pinMode(DIR_B_FL,OUTPUT);
     pinMode(PWM_FL,OUTPUT);
     pinMode(DIR_A_BR,OUTPUT);
     pinMode(DIR_B_BR,OUTPUT);
     pinMode(PWM_BR,OUTPUT);
     pinMode(DIR_A_BL,OUTPUT);
     pinMode(DIR_B_BL,OUTPUT);
     pinMode(PWM_BL,OUTPUT);
     pinMode(TRIG_F, OUTPUT );
     pinMode(ECHO_F, INPUT );
     pinMode(TRIG_B, OUTPUT );
     pinMode(ECHO_B, INPUT );

}

#endif
