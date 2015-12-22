/*************************************
          Implementation
          of library Motor.h
 *************************************/

#include "Motor.h"
#include "Arduino.h"

#define LEFTARROW 40
#define RIGHTARROW 41

namespace arduino_web_car
	{
			//Start class implementation

			motor::motor(const int& initial_DIR_A, const int& initial_DIR_B, const int &initial_PWM, int initial_gas)
				{
							DIR_A=initial_DIR_A;
							DIR_B=initial_DIR_B;
							PWM=initial_PWM;
							set_gas(initial_gas);
							state=arrest;
				}

			void motor::set_gas(int gas_supplied)
				{
								if(gas_supplied<mingas)
										gas_supplied=mingas;
								if(gas_supplied>maxgas)
										gas_supplied=maxgas;
								else
										gas=gas_supplied;
				}

			void motor::go_forwards(int gas_supplied)
				{
								set_gas(gas_supplied);
								digitalWrite(DIR_A,HIGH);
								digitalWrite(DIR_B,LOW);
								analogWrite (PWM,gas);
								state=on;
				}

      			void motor::go_forwards()
				{
								digitalWrite(DIR_A,HIGH);
								digitalWrite(DIR_B,LOW);
								analogWrite (PWM,gas);
								state=on;
				}

			void motor::go_backwards(int gas_supplied)
				{
								set_gas(gas_supplied);
								digitalWrite(DIR_A,LOW);
								digitalWrite(DIR_B,HIGH);
								analogWrite (PWM,gas);
								state=back;
				}

			void motor::go_backwards()
				{
								digitalWrite(DIR_A,LOW);
								digitalWrite(DIR_B,HIGH);
								analogWrite (PWM,gas);
								state=back;
				}

			void motor::stop()
				{
								analogWrite(PWM,0);
								state=arrest;
				}

			void motor::get_status()const
				{
								Serial.print("Velocità: ");
								Serial.println(gas);
								Serial.print("Status motore:");
								Serial.println(state);
				}

			motor::~motor()
				{
				}

			//END class implementation

			//First operation is implementing the controll of four motors at the same time

    	void go_forwards(motor fl,motor fr,motor br,motor bl)
        {
            		fl.go_forwards();
            		fr.go_forwards();
            		br.go_forwards();
            		bl.go_forwards();
                digitalWrite(40,LOW);
                digitalWrite(41,LOW);
        }

      void go_backwards(motor fl,motor fr,motor br,motor bl)
        {
            		fl.go_backwards();
            		fr.go_backwards();
            		br.go_backwards();
            		bl.go_backwards();
                digitalWrite(LEFTARROW,LOW);
                digitalWrite(RIGHTARROW,LOW);
        }

        void stop (motor fl,motor fr,motor br,motor bl)
        {
            		fl.stop();
            		fr.stop();
            		bl.stop();
            		br.stop();
                digitalWrite(LEFTARROW,LOW);
                digitalWrite(RIGHTARROW,LOW);
        }

        void go_left (motor fl,motor fr,motor br,motor bl)
        {
            		fl.go_forwards();
            		bl.go_forwards();
            		fr.go_backwards();
            		br.go_backwards();
                digitalWrite(LEFTARROW,HIGH);
                digitalWrite(RIGHTARROW,LOW);
        }

        void go_right(motor fl,motor fr,motor br,motor bl)
        {
            		fr.go_forwards();
            		br.go_forwards();
            		fl.go_backwards();
            		bl.go_backwards();
                digitalWrite(RIGHTARROW,HIGH);
                digitalWrite(LEFTARROW,LOW);
        }

        void set_gas (motor fl,motor fr,motor br,motor bl,int g)
        {
            		fl.set_gas(g);
            		fr.set_gas(g);
            		br.set_gas(g);
	            	bl.set_gas(g);
        }

}
