/*************************************
          Definition
          of serial menu for settings
          at run-time
 *************************************/

void menu()
  {
      boolean exit=false;
      while(!exit)
        {
            index();
            int r=0;  //support variable to see the number of input
            if(Serial.available())
              {
                  char c=Serial.read();
                  switch(c)
                    {
                        case 'C':  {
                                      Serial.println("Inserire 'SSID' e 'password'");
                                      Serial.println("Premere 'D' per i parametri di default");
                                      Serial.println();
                                      String ssid,pass,input;
                                      char character;
                                      while(r<2)
                                        {
                                          input="";
                                          while(Serial.available())
                                            {
                                                character = Serial.read();
                                                input.concat(character);
                                                delay(20);
                                            }
                                          if(input=="D")
                                            {
                                                Serial.println("Default");
                                                espm.wifiConnect();
                                                //espm.wifiConnect("Vodafone-33540710","9w2aucepc4lffw4");
                                                r=2;
                                            }
                                          else
                                            if((r<1)&&(input.length()>3))
                                              {
                                                  ssid=input;
                                                  r++;
                                              }
                                            else
                                              if((r<2)&&(input.length()>3))
                                                {
                                                    pass=input;
                                                    r++;
                                                }
                                        }
                                      if((ssid!="")&&(pass!=""))
                                        espm.wifiConnect(ssid,pass);
                                      espm.wifiSettings();
                                      break;
                                  } //END of connection case
                        case 'M':  {
                                      Serial.println("Inserire 'Client','User','Password','Broker','Port'");
                                      Serial.println("Premere 'D' per i parametri di default");
                                      Serial.println();
                                      String client,pass,user,broker,input;
                                      int port;
                                      char character;
                                      while(r<5)
                                        {
                                          input="";
                                          while(Serial.available())
                                            {
                                                character = Serial.read();
                      case 'E':  {
                                      Serial.println("Exit");
                                              }
                                          if(input=="D")
                                            {
                                                Serial.println("Default");
                                                r=5;
                                            }
                                          else
                                            if((r<1)&&(input.length()>0))
                                              {
                                                client=input;
                                                r++;
                                              }
                                            else
                                              if((r<2)&&(input.length()>0))
                                                {
                                                  user=input;
                                                  r++;
                                                }
                                              else
                                                if((r<3)&&(input.length()>0))
                                                  {
                                                    pass=input;
                                                    r++;
                                                  }
                                                else
                                                  if((r<4)&&(input.length()>0))
                                                    {
                                                      broker=input;
                                                      r++;
                                                    }
                                                  else
                                                    if((r<5)&&(input.length()>0))
                                                      {
                                                        port=input.toInt();
                                                        r++;
                                                      }
                                        }
                                      if((client!="")&&(pass!="")&&(user!="")&&(broker!="")&&(port>0))
                                        espm.setConnectMqtt(client,user ,pass ,broker,port);
                                      espm.connectMqtt();
                                      espm.checkStatus();
                                      break;
                                  }//END mqtt settings case
                        case 'S':  {
                                      String input;
                                      char character;
                                      Serial.println("Inserire Topic");
                                      Serial.println("Premere 'D' per i parametri di default");
                                      input="";
                                      while(Serial.available())
                                        {
                                            character = Serial.read();
                                            input.concat(character);
                                            delay(20);
                                        }
                                      if(input="D")
                                        espm.setSubscribe();
                                      else
                                        espm.setSubscribe(input);
                                      break;
                                  }
                        case 'c':  {
                                      espm.checkStatus();
                                      break;
                                  }
                        case 'E':  {
                                      Serial.println("Exit");
                                      exit=true;
                                      break;
                                  }
                        default:{
                                    Serial.println("Comando errato prego riprovare")
                                }
                    }//END CASE
              }// END if
        }// END while
  }//END function menu

void index()
  {
      Serial.println("Benvenuto nel menu' delle impostazioni");
      Serial.println("    Premere 'C' per connettersi a una rete WIFI");
      Serial.println("    Premere 'S' per impostare il topic dei comandi");
      Serial.println("    Premere 'M' per connettersi al broker mqtt (Bluemix)");
      Serial.println("    Premere 'c' per verificare lo stato della connessione");
      Serial.println("    Premere 'E' per uscire dal menu'");
      Serial.println();
  }
