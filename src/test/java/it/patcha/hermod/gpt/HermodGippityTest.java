package it.patcha.hermod.gpt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class HermodGippityTest {

	@Test
	public void testMainWithoutArgument() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		HermodGippity.main(new String[]{});
		String output = outContent.toString().trim();

		System.setOut(null);

		assertEquals("This is a stub, you put no argument", output);
	}

	@Test
	public void testMainWithArgument() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		String argument = "argument";
		HermodGippity.main(new String[]{argument});
		String output = outContent.toString().trim();

		System.setOut(null);

		assertEquals("This is a stub, your argument is: " + argument, output);
	}

}
