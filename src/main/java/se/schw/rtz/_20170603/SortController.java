package se.schw.rtz._20170603;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class SortController {

	private static final Logger LOGGER = Logger.getLogger(SortController.class);
	private ObjectMapper jsonMapper;
	private static final Object SEMAPHORE = new Object();

	@RequestMapping(value = "/sort", method = RequestMethod.POST)
	public SortResult<Double> sort(@RequestBody Double[] numbers) {
		SortResultBuilder<Double> builder = new SortResultBuilder<Double>();
		builder.unsortedList(numbers).timekeeperStart();

		if (numbers.length <= 1) {
			return builder.timekeeperEnd().sortedList(numbers).build();
		}

		while (!isSorted(numbers)) {
			int index1 = new Random().nextInt(numbers.length);
			int index2 = new Random().nextInt(numbers.length);

			boolean i1LessThanI2 = Integer.compare(index1, index2) < 0;
			boolean n1LessThanN2 = Double.compare(numbers[index1], numbers[index2]) < 0;

			if (i1LessThanI2 != n1LessThanN2) {
				swap(numbers, index1, index2);
				builder.markSwap();
			}
		}

		SortResult<Double> result = builder.timekeeperEnd().sortedList(numbers).build();

		storeResult(result);

		return result;
	}

	public static <T extends Comparable<T>> boolean isSorted(T[] numbers) {
		for (int i = 0; i < numbers.length - 1; i++) {
			if (numbers[i + 1].compareTo(numbers[i]) < 0) {
				return false;
			}
		}

		return true;
	}

	public static <T> void swap(T[] numbers, int index1, int index2) {
		T v1 = numbers[index1];
		T v2 = numbers[index2];

		numbers[index1] = v2;
		numbers[index2] = v1;
	}

	@RequestMapping(value = "/oldsort", method = RequestMethod.GET)
	public List<SortResult<Double>> getOldSorts() {
		synchronized (SEMAPHORE) {

			Path jsonFile = getStorageFile();

			try {
				if (!Files.exists(jsonFile)) {
					Files.write(jsonFile, getJsonMapper().writeValueAsBytes(Arrays.asList()));
					return Arrays.asList();
				}

				byte[] byteData = Files.readAllBytes(jsonFile);

				return getJsonMapper().readValue(byteData, new TypeReference<List<SortResult<Double>>>() {
				});
			} catch (IOException e) {
				LOGGER.warn("could not get old data... returning empty list.");
				return Arrays.asList();
			}
		}
	}

	private ObjectMapper getJsonMapper() {
		if (jsonMapper == null) {
			jsonMapper = new ObjectMapper();
		}
		return jsonMapper;
	}

	private Path getStorageFile() {
		String homeDir = System.getProperty("user.dir");
		String fileName = homeDir + "/hm-sort.json";
		File jsonFile = new File(fileName);
		return jsonFile.toPath();
	}

	private void storeResult(SortResult<Double> result) {
		synchronized (SEMAPHORE) {
			List<SortResult<Double>> oldSorts = new ArrayList<>(getOldSorts());
			oldSorts.add(result);

			Path jsonFile = getStorageFile();

			try {
				Files.write(jsonFile, getJsonMapper().writeValueAsBytes(oldSorts));
			} catch (JsonProcessingException e) {
				String message = String.format("could not store object because %s says %s",
						e.getClass().getSimpleName(), e.getMessage());
				LOGGER.fatal(message, e);
				throw new RuntimeException(message, e);
			} catch (IOException e) {
				String message = String.format("could not store object because %s says %s",
						e.getClass().getSimpleName(), e.getMessage());
				LOGGER.fatal(message, e);
				throw new RuntimeException(message, e);
			}
		}

	}
}