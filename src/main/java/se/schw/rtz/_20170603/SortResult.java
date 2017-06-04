package se.schw.rtz._20170603;

import java.util.List;

public class SortResult<T extends Comparable<T>> {

	private long elapsedTime;
	private long swaps;
	private List<T> sortedList;
	private List<T> unsortedList;

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setSwaps(long swaps) {
		this.swaps = swaps;
	}

	public long getSwaps() {
		return swaps;
	}

	public void setSortedList(List<T> sortedList2) {
		this.sortedList = sortedList2;
	}

	public List<T> getSortedList() {
		return sortedList;
	}

	public void setUnsortedList(List<T> unsortedList) {
		this.unsortedList = unsortedList;
	}

	public List<T> getUnsortedList() {
		return unsortedList;
	}

	@Override
	public String toString() {
		return "SortResult [elapsedTime=" + elapsedTime + ", swaps=" + swaps + ", sortedList=" + sortedList
				+ ", unsortedList=" + unsortedList + "]";
	}

	
}
