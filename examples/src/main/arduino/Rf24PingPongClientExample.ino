#include <SPI.h>
#include "nRF24L01.h"
#include "RF24.h"
#include "printf.h"

//
// Hardware configuration
//

// Set up nRF24L01 radio on SPI bus plus pins 7 & 8
RF24 radio(7,8);

//
// Topology
//

// Radio pipe addresses for the 2 nodes to communicate.
byte pipes[][6] = {"1Node","2Node"};

void setup(void)
{
  //
  // Print preamble
  //

  Serial.begin(57600);
  printf_begin();
  printf("\n\rClient for RF24 examples. Look more on https://github.com/magx2/rf24-java-jni\n\r");

  //
  // Setup and configure rf radio
  //

  radio.begin();

  // optionally, increase the delay between retries & # of retries
  radio.setRetries(15,15);

  // optionally, reduce the payload size.  seems to
  // improve reliability
  radio.setPayloadSize(8);

    radio.openWritingPipe(pipes[1]);
    radio.openReadingPipe(1,pipes[0]);

  //
  // Start listening
  //

  radio.startListening();

  //
  // Dump the configuration of the rf unit for debugging
  //

  radio.printDetails();
}

void loop(void)
{
	// if there is data ready
	if ( radio.available() )
	{
		// Dump the payloads until we've gotten everything
		signed long long got_time;

		// Fetch the payload, and see if this was the last one.
		radio.read( &got_time, sizeof(signed long long) );

		// Spew it
		printf("Got payload. ");
		// TODO dont know how to print signed long long
		Serial.print("millis() == ");
		Serial.print(millis());
		Serial.print("...");

		// Delay just a little bit to let the other unit
		// make the transition to receiver
		delay(20);

		// First, stop listening so we can talk
		radio.stopListening();

		// Send the final one back.
		radio.write( &got_time, sizeof(signed long long) );
		printf("Sent response.\n\r");

		// Now, resume listening so we catch the next packets.
		radio.startListening();
	}
}
