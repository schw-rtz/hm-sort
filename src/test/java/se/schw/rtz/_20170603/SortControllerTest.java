package se.schw.rtz._20170603;

import org.junit.Assert;
import org.junit.Test;

public class SortControllerTest {

	@Test
	public void test_isSorted() {
		Assert.assertTrue(SortController.isSorted(new Double[] { 1.0, 2.0 }));
		Assert.assertFalse(SortController.isSorted(new Double[] { 3.0, 2.0 }));
		Assert.assertTrue(SortController.isSorted(new Double[] { 2.0, 2.0 }));
	}

	@Test
	public void test_swap() {
		Double[] numbers = new Double[] { 3.0, 2.0 };
		SortController.swap(numbers, 0, 1);

		Assert.assertEquals(Double.valueOf(2.0), numbers[0], 0.001);
		Assert.assertEquals(Double.valueOf(3.0), numbers[1], 0.001);
	}

	@Test
	public void test_normalSort() {
		SortController subject = new SortController();
		SortResult<Double> result = subject.sort(new Double[] { 3.0, 2.0 });

		// det är en dålig ide att mäta tiden här...
		Assert.assertEquals(1L, result.getSwaps());
		Assert.assertEquals(Double.valueOf(3.0), result.getUnsortedList().get(0), 0.001);
		Assert.assertEquals(Double.valueOf(2.0), result.getUnsortedList().get(1), 0.001);
		Assert.assertEquals(Double.valueOf(2.0), result.getSortedList().get(0), 0.001);
		Assert.assertEquals(Double.valueOf(3.0), result.getSortedList().get(1), 0.001);
	}

	@Test
	public void test_zeroSort() {
		SortController subject = new SortController();
		SortResult<Double> result = subject.sort(new Double[] {});

		// det är en dålig ide att mäta tiden här...
		Assert.assertEquals(0L, result.getSwaps());
		Assert.assertTrue(result.getSortedList().isEmpty());
		Assert.assertTrue(result.getUnsortedList().isEmpty());
	}

	@Test
	public void test_oneSort() {
		SortController subject = new SortController();
		SortResult<Double> result = subject.sort(new Double[] { 2.0 });

		// det är en dålig ide att mäta tiden här...
		Assert.assertEquals(0L, result.getSwaps());
		Assert.assertTrue(result.getSortedList().size() == 1);
		Assert.assertTrue(result.getSortedList().size() == 1);
	}
}
