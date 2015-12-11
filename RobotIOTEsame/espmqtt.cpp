/*************************************
          Implementation
          of library espmqtt.h
 *************************************/

 #include "espmqtt.h"

namespace arduino_web_car
{

  void espmqtt::wifiConnect (String ssid,String password)
    {
        ssidwifi=ssid;
        passwordwifi=password;
        sendtoesp("connectAP('"+ssid+"','"+password+"')");
    }

  void espmqtt::wifiConnect ()
    {
        sendtoesp("connectAP('"+ssidwifi+"','"+passwordwifi+"')");
    }

  void espmqtt::sendtoesp(String message) const
    {
        esp->println(message);
        esp->flush();
    }

/*Construttore
  Precondition :rxpin and txpin not Equal and different from 1 and 0
  Postcondition: the object was created */

  espmqtt::espmqtt(const int rxin,const int txin)
    {
      if((rxin!=txin)||(rx==0 && tx==1)||(tx==0 && rx==1)||(tx==0 && rx==0))
        {
          esp=new SoftwareSerial (rxin,txin);
          esp->begin(9600);
          tx=txin;
          rx=rxin;
        }
        else
          Serial.println("Errore pin Rx e Tx");
    }
/* Distruttore */
  espmqtt::~espmqtt()
    {
        delete esp;
    }

/*This function return the string that the arduibno recive from ESP8266,
  if the ESP8266 don't send a message the function return "0"*/

  String espmqtt::recive()
    {
        boolean readcom=false;
        int i=0;
        char c[100];
        if (esp->find("[("))
          {
              esp->readBytes(c, 100);
              readcom=true;
          }
          esp->flush();
        if(readcom)
            return String(c);
        else
            return "0";
    }

/* Version of recive that recive one char */
  char espmqtt::recivechar()
    {
        bool readcom=false;
        char c[1];
        if (esp->find("[("))
          {
              esp->readBytes(c, 1);
              readcom=true;
          }
        if(readcom)
            return c[0];
        else
            return '0';
    }

  void espmqtt::setConnectMqtt(String client,String user ,String password ,String broke,int por)
    {
        clientID=client;
        userID=user;
        passwordID=password;
        broker=broke;
        port=port;
    }

  void espmqtt::connectMqtt() const
    {
        setSubscribe();
        delay(750);
        sendtoesp("mqttconnect('"+clientID+"','"+userID+"','"+passwordID+"','"+broker+"','"+port+"')");
    }

  void espmqtt::setSubscribe(String topicin)
    {
        topic=topicin;
        sendtoesp("SetSubTopic('iot-2/cmd/"+topic+"/fmt/+')");
    }
  void espmqtt::setSubscribe() const
    {
        sendtoesp("SetSubTopic('iot-2/cmd/"+topic+"/fmt/+')");
    }

  void espmqtt::publish(String topic,String message) const
    {
        sendtoesp("mqttPublish('iot-2/evt/"+topic+"/fmt/json','"+message+"')");
    }


/*This function ask at ESP8266 the status of connection and
  print the state, wifi settings and mqtt settings on serial monitor */

  void espmqtt::checkStatus()
    {
        bool readcom=false;
        char c[1];
        sendtoesp("checkStatus()");
          if (esp->find("(["))
            {
              esp->readBytes(c, 1);
                switch(c[0])
                  {
                      case 'W': {
                                    Serial.println();
                                    Serial.print("Connesso con il seguente ip");
                                    sendtoesp("=wifi.sta.getip()");
                                    //aspect not to interfere with the response of the ESP
                                      delay(1000);
                                    while(esp->available())
                                      Serial.print(char(esp->read()));
                                    Serial.println();
                                    break;
                                  }
                      case 'w': {
                                    Serial.println("Non connesso");
                                    break;
                                }
                      default:  {
                                    Serial.println("No informazioni sulla stato della connessione");
                                }
                  }
            }
        wifiSettings();
        mqttSettings();
    }
/*
    Delete the pair of parentheses in the message and adds the command
    for Arduino dedicated to the printing of text on the phone display
    see ArduinoDisplay
*/
  char *convertmessage(String d)
    {
        int i=1;
            while((d[i]!=')')&&(d[i++]!=']'));
        char *temp=new char[i];
        i=1;
        temp[0]='/';
        temp[1]='w';
        int j=2;
        while((d[i]!=')')&&(d[i+1]!=']'))
          temp[j++]=char(d[i++]);
        temp[j]='\0';
        return temp;
    }

  void espmqtt::wifiSettings ()
    {
        Serial.println();
        Serial.print("SSID:               ");
        Serial.println(ssidwifi);
        Serial.print("Password:           ");
        Serial.println(passwordwifi);
    }

  void espmqtt::mqttSettings()
    {
        Serial.println();
        Serial.print("Organization :     ");
        Serial.println(broker);
        Serial.print("Client :           ");
        Serial.println(clientID);
        Serial.print("User :             ");
        Serial.println(userID);
        Serial.print("Password mqtt :    ");
        Serial.println(passwordID);
        Serial.print("Port :             ");
        Serial.println(port);
        Serial.print("Topic :            ");
        Serial.println(topic);
    }
}
