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
     * @throws UnsupportedFileFormatException Если формат файла не поддерживается.
     * @throws IOException Если произошла ошибка при чтении файла.
     */
    public String analyzeFile(File file) throws UnsupportedFileFormatException, IOException {
        // Получаем расширение файла из имени
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        // Проверяем, поддерживается ли расширение
        if (!database.isSupportedExtension(fileExtension)) {
            throw new UnsupportedFileFormatException("Unsupported file extension: " + fileExtension);
        }

        // Получаем магическое число файла
        String magicNumber = getMagicNumber(file);
        AppLogger.info("Analyzing file: " + fileName + ", magic number: " + magicNumber);

        // Ищем расширение по магическому числу
        String extension = database.getExtensionByMagicNumber(magicNumber);

        // Если расширение не найдено, выбрасываем исключение
        if (extension == null) {
            throw new UnsupportedFileFormatException("Unsupported file format: " + fileName);
        }

        return extension;
    }

    /**
     * Читает магическое число из файла.
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