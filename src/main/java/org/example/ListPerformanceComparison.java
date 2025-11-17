package org.example;
import java.util.*;

public class ListPerformanceComparison {
    private static final int OPERATIONS_COUNT = 10000;

    public static List<TestResult> compareArrayListAndLinkedList() {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        List<TestResult> arrayListResults = performTests(arrayList);
        List<TestResult> linkedListResults = performTests(linkedList);

        List<TestResult> allResults = new ArrayList<>();
        allResults.addAll(arrayListResults);
        allResults.addAll(linkedListResults);

        return allResults;
    }

    private static List<TestResult> performTests(List<Integer> list) {
        List<TestResult> results = new ArrayList<>();

        results.add(new TestResult(list.getClass().getSimpleName(), "add(в конец)", testAddToEnd(list)));
        results.add(new TestResult(list.getClass().getSimpleName(), "add(в начало)", testAddToBeginning(list)));
        results.add(new TestResult(list.getClass().getSimpleName(), "add(в середину)", testAddToMiddle(list)));
        results.add(new TestResult(list.getClass().getSimpleName(), "get(случайный)", testGetRandom(list)));
        results.add(new TestResult(list.getClass().getSimpleName(), "get(первый)", testGetFirst(list)));
        results.add(new TestResult(list.getClass().getSimpleName(), "get(последний)", testGetLast(list)));
        results.add(new TestResult(list.getClass().getSimpleName(), "remove(из конца)", testRemoveFromEnd(list)));
        results.add(new TestResult(list.getClass().getSimpleName(), "remove(из начала)", testRemoveFromBeginning(list)));
        results.add(new TestResult(list.getClass().getSimpleName(), "remove(из середины)", testRemoveFromMiddle(list)));
        results.add(new TestResult(list.getClass().getSimpleName(), "contains(поиск)", testSearch(list)));
        results.add(new TestResult(list.getClass().getSimpleName(), "iteration(перебор)", testIteration(list)));

        return results;
    }

    private static long testAddToEnd(List<Integer> list) {
        long startTime = System.nanoTime();
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.add(i);
        }
        return System.nanoTime() - startTime;
    }

    private static long testAddToBeginning(List<Integer> list) {
        long startTime = System.nanoTime();
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.add(0, i);
        }
        return System.nanoTime() - startTime;
    }

    private static long testAddToMiddle(List<Integer> list) {
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.add(i);
        }

        long startTime = System.nanoTime();
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.add(list.size() / 2, i);
        }
        return System.nanoTime() - startTime;
    }

    private static long testGetRandom(List<Integer> list) {
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.add(i);
        }

        Random random = new Random();
        long startTime = System.nanoTime();
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.get(random.nextInt(list.size()));
        }
        return System.nanoTime() - startTime;
    }

    private static long testGetFirst(List<Integer> list) {
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.add(i);
        }

        long startTime = System.nanoTime();
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.get(0);
        }
        return System.nanoTime() - startTime;
    }

    private static long testGetLast(List<Integer> list) {
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.add(i);
        }

        long startTime = System.nanoTime();
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.get(list.size() - 1);
        }
        return System.nanoTime() - startTime;
    }

    private static long testRemoveFromEnd(List<Integer> list) {
        for (int i = 0; i < OPERATIONS_COUNT * 2; i++) {
            list.add(i);
        }

        long startTime = System.nanoTime();
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.remove(list.size() - 1);
        }
        return System.nanoTime() - startTime;
    }

    private static long testRemoveFromBeginning(List<Integer> list) {
        for (int i = 0; i < OPERATIONS_COUNT * 2; i++) {
            list.add(i);
        }

        long startTime = System.nanoTime();
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.remove(0);
        }
        return System.nanoTime() - startTime;
    }

    private static long testRemoveFromMiddle(List<Integer> list) {
        for (int i = 0; i < OPERATIONS_COUNT * 2; i++) {
            list.add(i);
        }

        long startTime = System.nanoTime();
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.remove(list.size() / 2);
        }
        return System.nanoTime() - startTime;
    }

    private static long testSearch(List<Integer> list) {
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.add(i);
        }

        long startTime = System.nanoTime();
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.contains(i);
        }
        return System.nanoTime() - startTime;
    }

    private static long testIteration(List<Integer> list) {
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.add(i);
        }

        long startTime = System.nanoTime();
        int sum = 0;
        for (Integer num : list) {
            sum += num;
        }
        return System.nanoTime() - startTime;
    }

    static class TestResult {
        String listType;
        String operation;
        long time;

        TestResult(String listType, String operation, long time) {
            this.listType = listType;
            this.operation = operation;
            this.time = time;
        }
    }
}