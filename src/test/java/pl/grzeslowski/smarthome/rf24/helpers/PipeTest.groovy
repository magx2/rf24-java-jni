package pl.grzeslowski.smarthome.rf24.helpers

import spock.lang.Specification;

public class PipeTest extends Specification {
    void "should parse string to proper long value"() {
		when: "creates pipe with string"
		final pipe = new Pipe(stringValue)

		then: "pipe should have proper long value"
		pipe.pipe == Long.parseLong(binValue, 2)

		where:
		stringValue | binValue
		"1Node"     | "110010101100100011011110100111000110001"
		"2Node"     | "110010101100100011011110100111000110010"
		"Node"      | "000000001100101011001000110111101001110"
    }
}
