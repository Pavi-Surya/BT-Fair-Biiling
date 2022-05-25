/**
 * 
 */
package com.wipro.bt.testpackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wipro.bt.beanpackage.InputDO;
import com.wipro.bt.beanpackage.OutputDO;
import com.wipro.bt.mainpackage.MainClass;

/**
 * @author Pavithra R
 *
 */
class SampleTest {

	private static List<OutputDO> outputList = new ArrayList<>();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("Assigning output values manually computed...");

		OutputDO output1 = new OutputDO("ALICE99", 4, 240l);
		OutputDO output2 = new OutputDO("CHARLIE", 3, 37l);
		outputList.add(output1);
		outputList.add(output2);
		System.out.println("Initialization Done...");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFunctionality() {
		String[] strArray = new String[] { "G:\\Pavithra\\StudyMaterial\\Java_TestCase\\SessionSummary_1.txt" };

		List<InputDO> dataList = MainClass.createDataObjectList(strArray[0]);
		InputDO firstLogTimeDO = dataList.stream().collect(Collectors.minBy(Comparator.comparing(InputDO::getTimeVal)))
				.get();
		LocalTime firstLogTime = firstLogTimeDO.getTimeVal();
		InputDO lastLogTimeDO = dataList.stream().collect(Collectors.maxBy(Comparator.comparing(InputDO::getTimeVal)))
				.get();
		LocalTime lastLogTime = lastLogTimeDO.getTimeVal();
		assertEquals(outputList, MainClass.getRequiredOutput(firstLogTime, lastLogTime, dataList));
		// fail("Not yet implemented");
	}

	@Test
	void testInputFileException() throws ArrayIndexOutOfBoundsException {
		String[] strArray = new String[] { "G:\\Pavithra\\StudyMaterial\\Java_TestCase\\SessionSummaryCode_1.txt" };
		assertEquals(null, MainClass.createDataObjectList(strArray[0]));
	}

	@Test
	void testInputDataException() {
		String[] strArray = new String[] { "G:\\Pavithra\\StudyMaterial\\Java_TestCase\\SessionSummary_2.txt" };

		List<InputDO> dataList = MainClass.createDataObjectList(strArray[0]);
		LocalTime firstLogTime = LocalTime.of(0, 0, 0);
		LocalTime lastLogTime = LocalTime.of(23, 59, 59);
		assertEquals(null, MainClass.getRequiredOutput(firstLogTime, lastLogTime, dataList));
		// fail("Not yet implemented");
	}

	@Test
	void testNullDataException() {
		List<InputDO> dataList = null;
		LocalTime firstLogTime = LocalTime.of(0, 0, 0);
		LocalTime lastLogTime = LocalTime.of(23, 59, 59);
		assertEquals(null, MainClass.compute("user", dataList, firstLogTime, lastLogTime));
		// fail("Not yet implemented");
	}

	@Test
	void testMainMethod() {		
		String[] strArray = new String[] { "G:\\Pavithra\\StudyMaterial\\Java_TestCase\\SessionSummary_1.txt" };
		MainClass.main(strArray);
		assertNotNull(strArray);
		// fail("Not yet implemented");
	}
	
	@Test
	void testMainMethodNull() {		
		String[] strArray = null;
		MainClass.main(strArray);
		assertNull(strArray);
		// fail("Not yet implemented");
	}
	
	@Test
	void testMainMethodEmpty() {		
		String[] strArray = {};
		MainClass.main(strArray);
		assertNotNull(strArray);
		// fail("Not yet implemented");
	}

}
