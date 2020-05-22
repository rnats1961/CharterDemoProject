package com.charter.example.customer;

import java.util.function.Predicate;

@FunctionalInterface
public interface CustomerPredicate<T> extends Predicate<T> {

	public boolean test(T t);

	public static Predicate<Customer> isAmountGT100(int threshold) {
		return c -> c.getAmount() > threshold;
	}

	public static Predicate<Customer> isAmountGT50(int threshold) {
		return c -> c.getAmount() > threshold;
	}

}
