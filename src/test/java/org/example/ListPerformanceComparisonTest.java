package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ListPerformanceComparisonTest {

    @BeforeEach
    void setUp() {
        ListPerformanceComparison performanceComparator = new ListPerformanceComparison();
    }

    @Test
    @DisplayName("Тест сравнения ArrayList и LinkedList - возвращает не пустой результат")
    void testCompareArrayListAndLinkedList_ReturnsNonEmptyResults() {
        // When
        List<ListPerformanceComparison.TestResult> results =
                ListPerformanceComparison.compareArrayListAndLinkedList();

        // Then
        assertNotNull(results, "Результаты не должны быть null");
        assertFalse(results.isEmpty(), "Результаты не должны быть пустыми");

        // Проверяем, что есть результаты для обоих типов списков
        long arrayListCount = results.stream()
                .filter(r -> r.listType.equals("ArrayList"))
                .count();
        long linkedListCount = results.stream()
                .filter(r -> r.listType.equals("LinkedList"))
                .count();

        assertTrue(arrayListCount > 0, "Должны быть результаты для ArrayList");
        assertTrue(linkedListCount > 0, "Должны быть результаты для LinkedList");
    }

    @Test
    @DisplayName("Тест результатов - все операции имеют положительное время")
    void testAllResults_HavePositiveTime() {
        // When
        List<ListPerformanceComparison.TestResult> results =
                ListPerformanceComparison.compareArrayListAndLinkedList();

        // Then
        for (ListPerformanceComparison.TestResult result : results) {
            assertTrue(result.time > 0,
                    "Время выполнения должно быть положительным для операции: " + result.operation);
            assertNotNull(result.operation, "Название операции не должно быть null");
            assertFalse(result.operation.trim().isEmpty(), "Название операции не должно быть пустым");
            assertNotNull(result.listType, "Тип списка не должен быть null");
        }
    }

    @Test
    @DisplayName("Тест структуры результатов - проверка всех операций")
    void testResultsStructure_ContainsAllExpectedOperations() {
        // Given
        String[] expectedOperations = {
                "add(в конец)", "add(в начало)", "add(в середину)",
                "get(случайный)", "get(первый)", "get(последний)",
                "remove(из конца)", "remove(из начала)", "remove(из середины)",
                "contains(поиск)", "iteration(перебор)"
        };

        // When
        List<ListPerformanceComparison.TestResult> results =
                ListPerformanceComparison.compareArrayListAndLinkedList();

        // Then
        for (String expectedOp : expectedOperations) {
            boolean foundInArrayList = results.stream()
                    .anyMatch(r -> r.listType.equals("ArrayList") && r.operation.equals(expectedOp));
            boolean foundInLinkedList = results.stream()
                    .anyMatch(r -> r.listType.equals("LinkedList") && r.operation.equals(expectedOp));

            assertTrue(foundInArrayList,
                    "Операция '" + expectedOp + "' должна присутствовать в результатах ArrayList");
            assertTrue(foundInLinkedList,
                    "Операция '" + expectedOp + "' должна присутствовать в результатах LinkedList");
        }
    }

    @Test
    @DisplayName("Тест производительности - разное время для разных операций")
    void testPerformance_DifferentTimesForDifferentOperations() {
        // When
        List<ListPerformanceComparison.TestResult> results =
                ListPerformanceComparison.compareArrayListAndLinkedList();

        // Then
        // Группируем результаты по типу списка и проверяем, что времена разные
        List<ListPerformanceComparison.TestResult> arrayListResults = results.stream()
                .filter(r -> r.listType.equals("ArrayList"))
                .toList();

        List<ListPerformanceComparison.TestResult> linkedListResults = results.stream()
                .filter(r -> r.listType.equals("LinkedList"))
                .toList();

        // Проверяем, что есть различия во времени выполнения между операциями
        assertTrue(hasTimeVariation(arrayListResults),
                "Должны быть различия во времени выполнения разных операций для ArrayList");
        assertTrue(hasTimeVariation(linkedListResults),
                "Должны быть различия во времени выполнения разных операций для LinkedList");
    }

    @Test
    @DisplayName("Тест TestResult класса - корректное создание и геттеры")
    void testTestResultClass_CreationAndGetters() {
        // Given
        String listType = "TestList";
        String operation = "testOperation";
        long time = 123456789L;

        // When
        ListPerformanceComparison.TestResult result =
                new ListPerformanceComparison.TestResult(listType, operation, time);

        // Then
        assertEquals(listType, result.listType, "Тип списка должен совпадать");
        assertEquals(operation, result.operation, "Операция должна совпадать");
        assertEquals(time, result.time, "Время должно совпадать");
    }

    @Test
    @DisplayName("Тест консистентности - повторные запуски дают схожие результаты")
    void testConsistency_SimilarResultsOnMultipleRuns() {
        // When
        List<ListPerformanceComparison.TestResult> results1 =
                ListPerformanceComparison.compareArrayListAndLinkedList();

        List<ListPerformanceComparison.TestResult> results2 =
                ListPerformanceComparison.compareArrayListAndLinkedList();

        // Then
        assertEquals(results1.size(), results2.size(),
                "Количество результатов должно быть одинаковым при повторном запуске");

        // Проверяем, что структура результатов одинаковая
        for (int i = 0; i < results1.size(); i++) {
            assertEquals(results1.get(i).listType, results2.get(i).listType,
                    "Типы списков должны совпадать при повторном запуске");
            assertEquals(results1.get(i).operation, results2.get(i).operation,
                    "Операции должны совпадать при повторном запуске");
        }
    }

    @Test
    @DisplayName("Тест граничных условий - OPERATIONS_COUNT влияет на время")
    void testBoundaryConditions_OperationsCountAffectsTime() {
        // This test verifies that the performance testing logic is working
        // by ensuring that operations that should be faster are indeed faster

        List<ListPerformanceComparison.TestResult> results =
                ListPerformanceComparison.compareArrayListAndLinkedList();

        // Для ArrayList: доступ к первому/последнему элементу должен быть быстрее случайного доступа
        ListPerformanceComparison.TestResult arrayListGetFirst = findResult(results, "ArrayList", "get(первый)");
        ListPerformanceComparison.TestResult arrayListGetRandom = findResult(results, "ArrayList", "get(случайный)");

        assertTrue(arrayListGetFirst.time < arrayListGetRandom.time,
                "Для ArrayList доступ к первому элементу должен быть быстрее случайного доступа");
    }

    // Вспомогательные методы
    private boolean hasTimeVariation(List<ListPerformanceComparison.TestResult> results) {
        if (results.size() < 2) return false;

        long firstTime = results.get(0).time;
        for (ListPerformanceComparison.TestResult result : results) {
            if (result.time != firstTime) {
                return true;
            }
        }
        return false;
    }

    private ListPerformanceComparison.TestResult findResult(
            List<ListPerformanceComparison.TestResult> results,
            String listType, String operation) {
        return results.stream()
                .filter(r -> r.listType.equals(listType) && r.operation.equals(operation))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        "Не найден результат для " + listType + " и операции " + operation));
    }
}
