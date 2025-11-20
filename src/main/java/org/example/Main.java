package org.example;
import java.util.List;
/*
 *@author Анастасия
 * @version 1.0
 * @see java.util.List
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("СРАВНЕНИЕ ПРОИЗВОДИТЕЛЬНОСТИ ARRAYLIST И LINKEDLIST");
        System.out.println("Количество операций для каждого теста: " + 10000);
        System.out.println("=".repeat(90));

        List<ListPerformanceComparison.TestResult> results = ListPerformanceComparison.compareArrayListAndLinkedList();

        printResultsTable(results);
    }

    private static void printResultsTable(List<ListPerformanceComparison.TestResult> results) {
        System.out.printf("%-15s | %-20s | %-12s | %-15s%n",
                "Тип списка", "Метод", "Кол-во операций", "Время (мс)");
        System.out.println("-".repeat(90));

        for (ListPerformanceComparison.TestResult result : results) {
            double timeMs = result.time / 1_000_000.0;
            System.out.printf("%-15s | %-20s | %,12d | %,13.3f%n",
                    result.listType, result.operation, 10000, timeMs);
        }
    }
}