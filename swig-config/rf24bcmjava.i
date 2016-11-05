%module rf24bcmjava

%{
#include "RF24.h"
%}
%include "../native/RF24/RF24.h"

RF24(uint8_t _cepin, uint8_t _cspin, uint32_t spispeed );