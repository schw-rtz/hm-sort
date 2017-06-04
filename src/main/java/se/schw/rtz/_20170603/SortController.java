package se.schw.rtz._20170603;

import java.util.Random;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SortController {

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

		return builder.timekeeperEnd().sortedList(numbers).build();
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

}
