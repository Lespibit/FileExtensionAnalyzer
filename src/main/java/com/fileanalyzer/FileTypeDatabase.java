package com.fileanalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для хранения информации о поддерживаемых форматах файлов.
 */
public class FileTypeDatabase {
    private final Map<String, String> magicNumberToExtension = new HashMap<>();
    /**
     * Конструктор, который загружает магические числа из файла.
     *
     * @param fileName Имя файла с магическими числами.
     * @throws IOException Если произошла ошибка при чтении файла.
     */
    public FileTypeDatabase(String fileName) throws IOException {
        loadMagicNumbersFromFile(fileName);
    }

    /**
     * Проверяет, поддерживается ли расширение файла.
     *
     * @param extension Расширение файла.
     * @return true, если расширение поддерживается, иначе false.
     */
    public boolean isExtensionSupported(String extension) {
        return magicNumberToExtension.containsValue(extension);
    }

    /**
     * Загружает магические числа из файла.
     *
     * @param fileName Имя файла с магическими числами.
     * @throws IOException Если произошла ошибка при чтении файла.
     */
    private void loadMagicNumbersFromFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String magicNumber = parts[0].trim();
                    String extension = parts[1].trim();
                    magicNumberToExtension.put(magicNumber, extension);
                }
            }
        }
    }

    /**
     * Возвращает расширение файла по магическому числу.
     *
     * @param magicNumber Магическое число.
     * @return Расширение файла или null, если магическое число не найдено.
     */
    public String getExtensionByMagicNumber(String magicNumber) {
        return magicNumberToExtension.get(magicNumber);
    }
}