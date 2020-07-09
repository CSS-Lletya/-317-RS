package com.runesource.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.IntStream;

/**
 * Represents the Index Queue of an Entity.
 * 
 * @author Dennis
 *
 */
public final class IndexQueue {

	/**
	 * The container element which may be appended or be removed from either end.
	 */
	private final Deque<Integer> container = new ArrayDeque<>();

	/**
	 * Constructs the {@link #IndexQueue(int)} and adds the element to it.
	 * 
	 * @param capacity
	 */
	public IndexQueue(int capacity) {
		IntStream.rangeClosed(1, capacity).forEach(container::add);
	}

	/**
	 * Pushes the value into the {@link #container}.
	 * 
	 * @param value
	 */
	public void openValue(int value) {
		container.push(value);
	}

	/**
	 * Gets the next value in the {@link #container}.
	 * 
	 * @return
	 */
	public int nextValue() {
		return container.poll();
	}
}