/*************************************
          Definition
          of library Motor.h
 *************************************/

 /*********************************************************************
    This library was created for controling the robot's motor.
    This type of library works for L298N Driver or similiary
    motor drive.
 ***********************************************************************/

#ifndef MOTOR_H_
#define MOTOR_H_
#define MAXGAS 255
#define MINGAS 50

namespace arduino_web_car
  {
      typedef  enum stat {on, back, arrest};

	     class motor {
         //ENUM TYPES and MEMBER CONSTANTS
           int static const mingas=MINGAS;
           int static const maxgas=MAXGAS;
	             public:
		              //COSTRUCTOR and DESTRUCTOR
		                motor(const int& initial_DIR_A, const int& initial_DIR_B, const int& initial_PWM,  int initial_gas=mingas);
		                ~motor();
		              //MODIFICATION MEMBER FUNCIONS
		                void go_forwards(int gas_supplied);
		                void go_forwards();
		                void go_backwards(int gas_supplied);
		                void go_backwards();
		                void stop();
		                void set_gas(int gas_supplied);
		              //CONSTANT MEMBER FUNCTIONS
		                stat get_status() const;	//Ricevo lo stato e la velocità/Get status and speed
	            private:
		                int gas;
		                stat state;
		              //CONTROL PINS
		                int DIR_A, DIR_B;
		                int PWM;
        	 }; //END CLASS DEFINITION

//Funzioni per controllare i quattro motori contemporaneamente/Functions for controlling four motors at the same time
        void stop (motor fl,motor fr,motor br,motor bl);
        void go_forwards(motor fl,motor fr,motor br,motor bl);
        void go_backwards(motor fl,motor fr,motor br,motor bl);
        void go_right(motor fl,motor fr,motor br,motor bl);
        void go_left(motor fl,motor fr,motor br,motor bl);
        void set_gas (motor fl,motor fr,motor br,motor bl,int g);
}

#endif /* MOTOR_H_ */
