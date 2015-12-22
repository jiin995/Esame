/*************************************
          Definition
          of library espmqtt.h
 *************************************/

 /*********************************************************************
    Note:The esp send messages from Arduino through the SoftwareSerial
    and the message comes in the form of characters, in particular
    the message will be of the type " [ (message) ] ".
    Brackets are used to suppress the reception of any interference
    that may occur on the cables that connect the two devices
***********************************************************************/

#include "SoftwareSerial.h"
#include "Arduino.h"
#ifndef ESPMQTT
#define ESPMQTT

namespace arduino_web_car
{
  class espmqtt{
    private :
        SoftwareSerial *esp;
        int rx,tx;
        String ssidwifi ="Vodafone-33540710";
        String passwordwifi="9w2aucepc4lffw4";
        String broker="mmmxa5.messaging.internetofthings.ibmcloud.com";
        String clientID="d:mmmxa5:ESP8266:18fe349c4963";
        String passwordID="hCp6umHa?H8R?rvoBr";
        String userID="use-token-auth";
        String topic="cmd1";
        int port=1883;
    public :
      //visualizzazione stato nel monitor seriale /showing the state of serial monitor
        void wifiSettings ();
        void mqttSettings();
        void checkStatus();

      //COSTRUTTORE e DISTRUTTORE/COSTRUCTOR and DESTRUCTOR
        espmqtt(const int rxin,const int txin);
        ~espmqtt();

      //connessione WI-FI/WI-FI connection
        void wifiConnect ();
        void wifiConnect (String ssid,String password);

        char recivechar();
      //ricezione comando da ESP8266/catching input from ESP8266
        String recive();
      //impostazione connessione mqtt/connection mqtt settings
        void setConnectMqtt(String client,String user ,String password ,String broke,int por);
      //connessione mqtt/mqtt connection
        void connectMqtt() const;
      //impostazione sub topic sull'ESP8266/ESP8266 sub topic settings
        void setSubscribe(String topic);
        void setSubscribe() const;
      //pubblica un messaggio su un determinato topic/show a message about a specific topic
        void publish(String topic,String message) const;
      //invia comandi all'ESP8266 tramite seriale/throwing serial input to ESP8266
        void sendtoesp(String message) const;
  };

/*
    Elimina la coppia di parentesi presenti nel messaggio e aggiunge il comando
    per l'arduino dedicato alla stampa del tel testo sul display
    vedere ArduinoDisplay
    /
    Delete the brackets of the message and add a print command for the display on the
    dedicated arduino.
    Refering ArduinoDisplay
*/
  char *convertmessage(String d);
}

#endif
