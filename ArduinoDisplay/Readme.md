Codice per Arduino per il controllo display.
I due dispositivi Arduino comunicano tra loro tramite una connessione seriale, utilizzando un architettura Master-Slave.
L'Arduino Master riceve i comandi tramite mqtt e quando determina che quel comando è un comando per il display provvede ad inviarlo 
all'Arduino Slave che interpreta e esegue il comando ricevuto