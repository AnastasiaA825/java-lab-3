package org.example;

import java.util.List;

/**
 * Основной класс приложения для сравнения производительности ArrayList и LinkedList.
 * Программа выполняет серию тестов для измерения времени выполнения основных операций
 * и выводит результаты в табличном формате.
 *
 * @author Анастасия
 * @version 1.0
 * @see java.util.List
 * @see ListPerformanceComparison
 * @see ListPerformanceComparison.TestResult
 */
public class Main {

    /**
     * Точка входа в приложение.
     * Инициирует выполнение тестов производительности ArrayList и LinkedList,
     * затем выводит результаты в консоль в табличном формате.
     *
     * @param args аргументы командной строки (не используются)
     *
     * @see #printResultsTable(List)
     * @see ListPerformanceComparison#compareArrayListAndLinkedList()
     */
    public static void main(String[] args) {
        System.out.println("СРАВНЕНИЕ ПРОИЗВОДИТЕЛЬНОСТИ ARRAYLIST И LINKEDLIST");
        System.out.println("Количество операций для каждого теста: " + 10000);
        System.out.println("=".repeat(90));

        List<ListPerformanceComparison.TestResult> results = ListPerformanceComparison.compareArrayListAndLinkedList();

        printResultsTable(results);
    }

    /**
     * Форматирует и выводит результаты тестирования в табличном виде.
     * Таблица содержит следующие столбцы:
     * - Тип списка (ArrayList/LinkedList)
     * - Тестируемая операция
     * - Количество выполненных операций
     * - Затраченное время в миллисекундах
     *
     * @param results список объектов TestResult с результатами тестирования
     *
     * @see ListPerformanceComparison.TestResult
     */
    private static void printResultsTable(List<ListPerformanceComparison.TestResult> results) {
        // Заголовок таблицы
        System.out.printf("%-15s | %-20s | %-12s | %-15s%n",
                "Тип списка", "Метод", "Кол-во операций", "Время (мс)");
        System.out.println("-".repeat(90));

        // Данные результатов
        for (ListPerformanceComparison.TestResult result : results) {
            double timeMs = result.time / 1_000_000.0; // Конвертация наносекунд в миллисекунды
            System.out.printf("%-15s | %-20s | %,12d | %,13.3f%n",
                    result.listType, result.operation, 10000, timeMs);
        }
    }
}