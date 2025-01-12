package com.fileanalyzer;

import java.io.File;
import java.io.IOException;

/**
 * Главный класс программы для восстановления расширений файлов.
 */
public class Main {
    /**
     * Точка входа в программу. Обрабатывает переданный файл и восстанавливает его расширение.
     *
     * @param args Аргументы командной строки. Ожидается один аргумент — путь к файлу.
     */
    public static void main(String[] args) {
        AppLogger.info("Starting application...");

        if (args.length == 0) {
            AppLogger.warn("No file path provided.");
            System.out.println("Usage: java -jar FileExtensionAnalyzer.jar <file_path>");
            return;
        }

        String filePath = args[0];
        AppLogger.info("Processing file: " + filePath);

        try {
            // Инициализация базы данных магических чисел
            FileTypeDatabase database = new FileTypeDatabase("magic_numbers.txt"); // Передаем имя файла
            FileAnalyzer analyzer = new FileAnalyzer(database);
            FileExtensionRestorer restorer = new FileExtensionRestorer(analyzer);

            File file = new File(filePath);
            if (!file.exists()) {
                AppLogger.warn("File not found: " + filePath);
                System.out.println("File not found: " + filePath);
                return;
            }

            // Восстановление расширения файла
            restorer.restoreExtension(file);
        } catch (IOException e) {
            ExceptionHandler.handleException(e, "Error initializing database");
        } catch (Exception e) {
            ExceptionHandler.handleException(e, "Error processing file");
        }
    }
}