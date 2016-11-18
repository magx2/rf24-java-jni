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
	printf("\n\rServer for RF24 examples. Look more on https://github.com/magx2/rf24-java-jni\n\r");

	//
	// Setup and configure rf radio
	//

	radio.begin();

	// optionally, increase the delay between retries & # of retries
	radio.setRetries(15,15);

	// optionally, reduce the payload size.  seems to
	// improve reliability
	radio.setPayloadSize(8);

	radio.openWritingPipe(pipes[0]);
	radio.openReadingPipe(1,pipes[1]);

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
	// First, stop listening so we can talk.
	radio.stopListening();

	// Take the time, and send it.  This will block until complete
	unsigned long time = millis();
	printf("Now sending %lu...",time);
	bool ok = radio.write( &time, sizeof(unsigned long) );

	if (ok)
	  printf("ok...");
	else
	  printf("failed.\n\r");

	// Now, continue listening
	radio.startListening();

	// Wait here until we get a response, or timeout (250ms)
	unsigned long started_waiting_at = millis();
	bool timeout = false;
	while ( ! radio.available() && ! timeout )
	  if (millis() - started_waiting_at > 200 )
		timeout = true;

	// Describe the results
	if ( timeout )
	{
	  printf("Failed, response timed out.\n\r");
	}
	else
	{
	  // Grab the response, compare, and send to debugging spew
	  unsigned long got_time;
	  radio.read( &got_time, sizeof(unsigned long) );

	  // Spew it
	  printf("Got response %lu, round-trip delay: %lu\n\r",got_time,millis()-got_time);
	}

	// Try again 1s later
	delay(1000);
}
