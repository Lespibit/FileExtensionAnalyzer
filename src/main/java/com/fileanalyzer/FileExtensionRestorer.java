package com.fileanalyzer;

import java.io.File;

/**
 * Класс для восстановления расширений файлов на основе анализа их содержимого.
 * <p>
 * Этот класс использует {@link FileAnalyzer} для определения правильного расширения файла
 * и переименовывает файл, добавляя это расширение, если оно отсутствует или не соответствует содержимому.
 * </p>
 */
public class FileExtensionRestorer {
    private final FileAnalyzer fileAnalyzer;

    /**
     * Конструктор класса FileExtensionRestorer.
     *
     * @param fileAnalyzer Анализатор файлов, используемый для определения расширения.
     */
    public FileExtensionRestorer(FileAnalyzer fileAnalyzer) {
        this.fileAnalyzer = fileAnalyzer;
    }

    /**
     * Восстанавливает расширение файла на основе его содержимого.
     * <p>
     * Метод выполняет следующие действия:
     * <ol>
     *   <li>Определяет расширение файла с помощью {@link FileAnalyzer}.</li>
     *   <li>Переименовывает файл, добавляя правильное расширение, если оно отсутствует.</li>
     *   <li>Логирует результат операции (успех или ошибка).</li>
     *</ol>
     * @param file Файл, для которого нужно восстановить расширение.
     */
    public void restoreExtension(File file) {
        try {
            String extension = fileAnalyzer.analyzeFile(file);
            String newFileName = file.getAbsolutePath() + "." + extension;
            File newFile = new File(newFileName);
            if (file.renameTo(newFile)) {
                AppLogger.info("File extension restored: " + newFile.getName());
                System.out.println("File extension restored: " + newFile.getName());
            } else {
                AppLogger.warn("Failed to restore extension for: " + file.getName());
                System.out.println("Failed to restore extension for: " + file.getName());
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, "Error restoring extension");
        }
    }
}