package com.fileanalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Класс для анализа файлов и определения их расширений на основе магических чисел.
 */
public class FileAnalyzer {
    private final FileTypeDatabase database;

    /**
     * Конструктор класса FileAnalyzer.
     *
     * @param database База данных магических чисел и расширений.
     */
    public FileAnalyzer(FileTypeDatabase database) {
        this.database = database;
    }

    /**
     * Анализирует файл и возвращает его расширение на основе магического числа.
     * <p>
     * Если формат файла не поддерживается, выбрасывает исключение {@link UnsupportedFileFormatException}.
     *
     * @param file Файл для анализа.
     * @return Расширение файла.
     * @throws UnsupportedFileFormatException Если формат файла не поддерживается или произошла ошибка при чтении файла.
     */
    public String analyzeFile(File file) throws UnsupportedFileFormatException {
        // Проверяем расширение файла
        String extension = getFileExtension(file);
        if (extension == null || !isSupportedFormat(extension)) {
            String errorMessage = "Unsupported file format: " + file.getName();
            UnsupportedFileFormatException exception = new UnsupportedFileFormatException(errorMessage);
            AppLogger.error(errorMessage, exception); // Логируем ошибку с исключением
            throw exception; // Выбрасываем исключение
        }

        try {
            // Получаем магическое число файла
            String magicNumber = getMagicNumber(file);
            AppLogger.info("Analyzing file: " + file.getName() + ", magic number: " + magicNumber);

            // Ищем расширение по магическому числу
            String detectedExtension = database.getExtensionByMagicNumber(magicNumber);

            // Если расширение не найдено, выбрасываем исключение
            if (detectedExtension == null) {
                String errorMessage = "Unsupported file format: " + file.getName();
                UnsupportedFileFormatException exception = new UnsupportedFileFormatException(errorMessage);
                AppLogger.error(errorMessage, exception); // Логируем ошибку с исключением
                throw exception; // Выбрасываем исключение
            }

            return detectedExtension;
        } catch (IOException e) {
            // Логируем ошибку и выбрасываем UnsupportedFileFormatException
            AppLogger.warn("Failed to read file: " + file.getName());
            throw new UnsupportedFileFormatException("Failed to read file: " + file.getName());
        }
    }

    /**
     * Извлекает расширение файла из его имени.
     *
     * @param file Файл для анализа.
     * @return Расширение файла (в нижнем регистре) или null, если расширение отсутствует.
     */
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastDotIndex = name.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return null; // Нет расширения
        }
        return name.substring(lastDotIndex + 1).toLowerCase();
    }

    /**
     * Проверяет, поддерживается ли расширение файла.
     *
     * @param extension Расширение файла.
     * @return true, если расширение поддерживается, иначе false.
     */
    private boolean isSupportedFormat(String extension) {
        return database.isExtensionSupported(extension);
    }

    /**
     * Читает магическое число из файла.
     * <p>
     * Магическое число — это первые 4 байта файла, представленные в виде шестнадцатеричной строки.
     *
     * @param file Файл для чтения.
     * @return Магическое число в виде шестнадцатеричной строки.
     * @throws IOException Если произошла ошибка при чтении файла.
     */
    private String getMagicNumber(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[4];
            int bytesRead = fis.read(bytes);
            if (bytesRead != bytes.length) {
                throw new IOException("Could not read enough bytes from the file.");
            }
            return bytesToHex(bytes);
        }
    }

    /**
     * Преобразует массив байтов в шестнадцатеричную строку.
     *
     * @param bytes Массив байтов.
     * @return Шестнадцатеричная строка.
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}