/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	public List<T> merge(List<T> first, List<T> second, Comparator<T> comparator) {
		List<T> sorted = new ArrayList<T>();
		int total = first.size() + second.size();
		int first_i = 0;
		int second_i = 0;
		while (first_i < first.size() && second_i < second.size()) {
			if (comparator.compare(first.get(first_i), second.get(second_i)) < 0) {
				sorted.add(first.get(first_i));
				first_i++;
			} else {
				sorted.add(second.get(second_i));
				second_i++;
			}
		}
		while (first_i < first.size()) {
			sorted.add(first.get(first_i));
			first_i++;
		}
		while (second_i < second.size()) {
			sorted.add(second.get(second_i));
			second_i++;
		}
		return sorted;
	}
	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        List<T> sortedList = new ArrayList<T>();
        if (list.size() == 1) return list;

        int half = list.size()/2;

        //sort first half
        List<T> firstHalf = list.subList(0, half);
        insertionSort(firstHalf, comparator);
        //sort second half
        List<T> secondHalf = list.subList(half, list.size());
        insertionSort(secondHalf, comparator);

		sortedList = merge(firstHalf, secondHalf, comparator);
       
		return sortedList;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public int max_index;

	public void swap(List<T> list, int i, int j) {
		T tmp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, tmp);
	}

	public void maxheap(List<T> list, int i, Comparator<T> comparator) {
		int max_index = list.size() - 1;
		int left = 2*i;
		int right = 2*i + 1;
		int max = i;
		if (left <= max_index && comparator.compare(list.get(i), list.get(left)) > 0) 
		{
			max = left;
		}
		if (right <= max_index && comparator.compare(list.get(max), list.get(right)) > 0)
		{
			max = right;
		}

		if (max != i) 
		{
			swap(list, i, max);
			maxheap(list, max, comparator);
		}
	}

	public void heapify(List<T> list, Comparator<T> comparator) {
		max_index = list.size() - 1;
		for (int i = max_index/2; i >= 0; i--) {
			maxheap(list, i, comparator);
		}
	}

	public void heapSort(List<T> list, Comparator<T> comparator) {
        heapify(list, comparator);
        for (int i = max_index; i > 0; i--) {
        	swap(list, 0, i);
        	max_index = max_index - 1;
        	maxheap(list, 0, comparator);
        }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        List<T> sorted = new ArrayList<T>();
        while (list.isEmpty() == false) {
        	T t = list.remove(0);
        	if (sorted.size() > k) {
        		if (comparator.compare(t, sorted.get(0)) > 0) {
        			sorted.remove(0);
        			sorted.add(t);
        			heapSort(sorted, comparator);
        		} //ignore if t is smaller than sorted
        	} else {
        		sorted.add(t);
        		heapSort(sorted, comparator);
        	}
        }
        sorted.remove(0);
        return sorted;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
