package com.ziffit.autoconfigure.util;

import com.google.common.collect.Range;

import java.util.Comparator;

/**
 * Causes a Guava {@link Range} collection to be ordered in ascending order,
 * first by lower endpoints, then if that's equal by upper endpoints.
 */
// TODO: Handle open / closed ranges, instead of treating everything as a closed range
public class RangeComparator<T extends Comparable<T>> implements Comparator<Range<T>> {

    @Override
    public int compare(Range<T> first, Range<T> second) {
        int comparatorResult = first.lowerEndpoint().compareTo(second.lowerEndpoint());
        return comparatorResult == 0 ? first.upperEndpoint().compareTo(second.upperEndpoint()) : comparatorResult;
    }
}
