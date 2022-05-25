package com.wipro.bt.mainpackage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.wipro.bt.beanpackage.InputDO;
import com.wipro.bt.beanpackage.OutputDO;

public class MainClass {

	private static final String START = "Start";
	private static final String END = "End";

	public static void main(String[] args) {
		try {
			if (args.length > 0) {
				// Creating data List
				List<InputDO> dataList = createDataObjectList(args[0]);

				// Getting First log Time and Last log Time
				InputDO firstLogTimeDO = dataList.stream()
						.collect(Collectors.minBy(Comparator.comparing(InputDO::getTimeVal))).get();
				LocalTime firstLogTime = firstLogTimeDO.getTimeVal();
				InputDO lastLogTimeDO = dataList.stream()
						.collect(Collectors.maxBy(Comparator.comparing(InputDO::getTimeVal))).get();
				LocalTime lastLogTime = lastLogTimeDO.getTimeVal();

				// Get session timing, count for users
				List<OutputDO> outputList = getRequiredOutput(firstLogTime, lastLogTime, dataList);
				;
				for (OutputDO data : outputList) {
					System.out.println(data.getUserName() + " " + data.getSessionCount() + " " + data.getSessionTime());
				}

			} else {
				System.out.println("No Input Parameters...");
			}

		} catch (Exception e) {
			System.out.println("Exception occured at MainClass -> main Method..." + e.toString());
		}
	}

	/**
	 * Takes the required input fields and provides the output as stated in the
	 * Statement file
	 */
	public static List<OutputDO> getRequiredOutput(LocalTime firstLogTime, LocalTime lastLogTime,
			List<InputDO> dataList) {
		try {
			List<OutputDO> outputList = new ArrayList<>();

			// Breaking session based on users
			Map<String, List<InputDO>> groupByUser = dataList.stream()
					.collect(Collectors.groupingBy(InputDO::getUserName));

			// Compute the session logs for each user
			groupByUser.forEach((user, list) -> {
				OutputDO outputDO = compute(user, list, firstLogTime, lastLogTime);
				outputList.add(outputDO);
			});
			return outputList;
		} catch (Exception e) {
			System.out.println("Exception occured at MainClass -> getRequiredOutput Method..." + e.toString());
		}
		return null;
	}

	/** Computes the session time and session count for Single user */
	public static OutputDO compute(String user, List<InputDO> list, LocalTime firstLogTime, LocalTime lastLogTime) {
		try {
			list.sort((InputDO ido1, InputDO ido2) -> ido1.getTimeVal().compareTo(ido2.getTimeVal()));
			Map<String, List<InputDO>> activityList = list.stream()
					.collect(Collectors.groupingBy(InputDO::getActivity));
			int sessionCount = 0;
			long sessionTime = 0l;
			List<InputDO> startList = activityList.get(START);
			List<InputDO> endList = activityList.get(END);
			for (InputDO inputDO : startList) {
				for (InputDO inputDO2 : endList) {
					if (inputDO2.getFlag().equals(Boolean.TRUE)
							&& inputDO.getTimeVal().isBefore(inputDO2.getTimeVal())) {
						sessionCount++;
						sessionTime += (inputDO.getTimeVal().until(inputDO2.getTimeVal(), ChronoUnit.SECONDS));
						inputDO.setFlag(Boolean.FALSE);
						inputDO2.setFlag(Boolean.FALSE);
						break;
					}
				}
			}
			for (InputDO inputDO : startList) {
				if (inputDO.getFlag().equals(Boolean.TRUE)) {
					sessionCount++;
					sessionTime += (inputDO.getTimeVal().until(lastLogTime, ChronoUnit.SECONDS));
				}
			}
			for (InputDO inputDO : endList) {
				if (inputDO.getFlag().equals(Boolean.TRUE)) {
					sessionCount++;
					sessionTime += (firstLogTime.until(inputDO.getTimeVal(), ChronoUnit.SECONDS));
				}
			}
			OutputDO outputDO = new OutputDO(user, sessionCount, sessionTime);
			return outputDO;
		} catch (Exception e) {
			System.out.println("Exception occured at MainClass -> compute Method..." + e.toString());
		}
		return null;
	}

	/**Read the text file and convert it to Java Data object List*/
	public static List<InputDO> createDataObjectList(String path) {
		try {
			byte[] bytes = Files.readAllBytes(Paths.get(path));
			String fileContent = new String(bytes);
			String[] inputArray = fileContent.split(System.lineSeparator());

			List<InputDO> dataList = new ArrayList<>();
			for (int iter = 0; iter < inputArray.length; iter++) {
				String iteration = inputArray[iter];
				String[] lineItem = iteration.split(" ");
				InputDO data = new InputDO(lineItem[0], lineItem[1], lineItem[2]);
				dataList.add(data);
			}
			return dataList;
		} catch (Exception e) {
			System.out.println("Exception occured at MainClass -> createDataObjectList Method..." + e.toString());
		}
		return null;
	}

}
