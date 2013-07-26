package com.mss.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;

@SuppressWarnings({"unchecked"})
public class IterableHelpers {
	
	public static <T> T[] toArray(Class<T> t, Iterable<T> elements) {
	    final ArrayList<T> arrayElements = new ArrayList<T>();
	    for (T element : elements) {
	        arrayElements.add(element);
	    }
	    final T[] ta = (T[]) Array.newInstance(t, arrayElements.size());
	    return arrayElements.toArray(ta);
	}
}
