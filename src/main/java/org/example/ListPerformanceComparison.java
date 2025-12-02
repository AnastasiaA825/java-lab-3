package org.example;

import java.util.*;

/**
 * Класс для сравнительного анализа производительности операций
 * в реализациях List: ArrayList и LinkedList.
 * Выполняет замер времени выполнения базовых операций над коллекциями
 * и возвращает результаты в виде структурированных данных.
 *
 * <p>Тестируемые операции включают:
 * <ul>
 *   <li>Добавление элементов (в начало, середину, конец)</li>
 *   <li>Получение элементов (первый, последний, случайный)</li>
 *   <li>Удаление элементов (из начала, середины, конца)</li>
 *   <li>Поиск элементов (contains)</li>
 *   <li>Итерация по всем элементам</li>
 * </ul>
 *
 * @author Анастасия
 * @version 1.0
 * @see ArrayList
 * @see LinkedList
 * @see Main
 */
public class ListPerformanceComparison {
    /** Количество операций для каждого теста производительности */
    private static final int OPERATIONS_COUNT = 10000;

    /**
     * Основной метод сравнения производительности ArrayList и LinkedList.
     * Выполняет идентичные тесты для обеих реализаций интерфейса List
     * и возвращает агрегированные результаты.
     *
     * @return список результатов тестирования для всех операций и обеих реализаций List
     *
     * @see #performTests(List)
     * @see TestResult
     */
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

    /**
     * Выполняет полный набор тестов производительности для конкретной реализации List.
     *
     * @param list тестируемая коллекция (должна быть пустой при вызове)
     * @return список результатов выполнения всех тестов для данной коллекции
     *
     * @see #testAddToEnd(List)
     * @see #testAddToBeginning(List)
     * @see #testAddToMiddle(List)
     * @see #testGetRandom(List)
     * @see #testGetFirst(List)
     * @see #testGetLast(List)
     * @see #testRemoveFromEnd(List)
     * @see #testRemoveFromBeginning(List)
     * @see #testRemoveFromMiddle(List)
     * @see #testSearch(List)
     * @see #testIteration(List)
     */
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

    /**
     * Тестирует производительность операции добавления элементов в конец списка.
     *
     * @param list список для тестирования
     * @return время выполнения в наносекундах
     */
    private static long testAddToEnd(List<Integer> list) {
        long startTime = System.nanoTime();
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.add(i);
        }
        return System.nanoTime() - startTime;
    }

    /**
     * Тестирует производительность операции добавления элементов в начало списка.
     *
     * @param list список для тестирования
     * @return время выполнения в наносекундах
     */
    private static long testAddToBeginning(List<Integer> list) {
        long startTime = System.nanoTime();
        for (int i = 0; i < OPERATIONS_COUNT; i++) {
            list.add(0, i);
        }
        return System.nanoTime() - startTime;
    }

    /**
     * Тестирует производительность операции добавления элементов в середину списка.
     * Перед началом замера времени заполняет список элементами для обеспечения
     * корректного определения середины.
     *
     * @param list список для тестирования
     * @return время выполнения в наносекундах
     */
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

    /**
     * Тестирует производительность операции получения случайных элементов.
     * Перед началом замера времени заполняет список элементами.
     *
     * @param list список для тестирования
     * @return время выполнения в наносекундах
     */
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

    /**
     * Тестирует производительность операции получения первого элемента.
     * Перед началом замера времени заполняет список элементами.
     *
     * @param list список для тестирования
     * @return время выполнения в наносекундах
     */
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

    /**
     * Тестирует производительность операции получения последнего элемента.
     * Перед началом замера времени заполняет список элементами.
     *
     * @param list список для тестирования
     * @return время выполнения в наносекундах
     */
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

    /**
     * Тестирует производительность операции удаления элементов из конца списка.
     * Перед началом замера времени заполняет список удвоенным количеством элементов
     * для предотвращения полной очистки во время теста.
     *
     * @param list список для тестирования
     * @return время выполнения в наносекундах
     */
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

    /**
     * Тестирует производительность операции удаления элементов из начала списка.
     * Перед началом замера времени заполняет список удвоенным количеством элементов
     * для предотвращения полной очистки во время теста.
     *
     * @param list список для тестирования
     * @return время выполнения в наносекундах
     */
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

    /**
     * Тестирует производительность операции удаления элементов из середины списка.
     * Перед началом замера времени заполняет список удвоенным количеством элементов
     * для предотвращения полной очистки во время теста.
     *
     * @param list список для тестирования
     * @return время выполнения в наносекундах
     */
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

    /**
     * Тестирует производительность операции поиска элементов (contains).
     * Перед началом замера времени заполняет список элементами.
     *
     * @param list список для тестирования
     * @return время выполнения в наносекундах
     */
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

    /**
     * Тестирует производительность операции итерации по всем элементам списка.
     * Перед началом замера времени заполняет список элементами.
     * Во время итерации выполняет простую операцию суммирования для предотвращения
     * оптимизации компилятором.
     *
     * @param list список для тестирования
     * @return время выполнения в наносекундах
     */
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

    /**
     * Внутренний класс для хранения результатов выполнения одного теста.
     * Содержит информацию о типе списка, тестируемой операции и времени выполнения.
     */
    static class TestResult {
        /** Тип списка (ArrayList или LinkedList) */
        String listType;

        /** Название тестируемой операции */
        String operation;

        /** Время выполнения операции в наносекундах */
        long time;

        /**
         * Конструктор для создания объекта результата теста.
         *
         * @param listType тип тестируемого списка
         * @param operation название операции
         * @param time время выполнения в наносекундах
         */
        TestResult(String listType, String operation, long time) {
            this.listType = listType;
            this.operation = operation;
            this.time = time;
        }
    }
}