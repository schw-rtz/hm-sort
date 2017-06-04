package se.schw.rtz._20170603;

import java.util.ArrayList;
import java.util.Arrays;

public class SortResultBuilder<T extends Comparable<T>> {

	private long timekeeperStart = -1L;
	private long timekeeperEnd = -1L;
	private long swaps = 0L;
	private ArrayList<T> sortedList;
	private ArrayList<T> unsortedList;

	public void timekeeperStart() {
		timekeeperStart = System.currentTimeMillis();
	}

	public SortResultBuilder<T> timekeeperEnd() {
		timekeeperEnd = System.currentTimeMillis();
		return this;
	}

	public SortResult<T> build() {
		SortResult<T> sortResult = new SortResult<T>();
		sortResult.setElapsedTime(timekeeperEnd - timekeeperStart);
		sortResult.setSwaps(swaps);
		sortResult.setSortedList(sortedList);
		sortResult.setUnsortedList(unsortedList);
		return sortResult;
	}

	public void markSwap() {
		swaps++;
	}

	public SortResultBuilder<T> sortedList(T[] numbers) {
		sortedList = new ArrayList<T>(Arrays.asList(numbers));
		return this;
	}

	public SortResultBuilder<T> unsortedList(T[] numbers) {
		unsortedList = new ArrayList<T>(Arrays.asList(numbers));
		return this;
	}

}
