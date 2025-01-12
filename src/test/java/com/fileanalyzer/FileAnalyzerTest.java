package com.fileanalyzer;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class FileAnalyzerTest {

    @Test
    public void testAnalyzeJpgFile() throws IOException, UnsupportedFileFormatException {
        // Загружаем файл magic_numbers.txt из ресурсов
        URL magicNumbersResource = getClass().getClassLoader().getResource("magic_numbers.txt");
        assertNotNull(magicNumbersResource, "Файл magic_numbers.txt не найден в ресурсах");

        FileTypeDatabase database = new FileTypeDatabase(magicNumbersResource.getFile());
        FileAnalyzer analyzer = new FileAnalyzer(database);

        // Загружаем файл из ресурсов
        URL resource = getClass().getClassLoader().getResource("test.jpg");
        assertNotNull(resource, "Файл test.jpg не найден в ресурсах");
        File file = new File(resource.getFile());

        // Анализируем файл
        String extension = analyzer.analyzeFile(file);

        // Проверяем, что расширение корректно распознано
        assertEquals("jpg", extension, "Расширение файла должно быть jpg");
    }

    @Test
    public void testAnalyzePngFile() throws IOException, UnsupportedFileFormatException {
        // Загружаем файл magic_numbers.txt из ресурсов
        URL magicNumbersResource = getClass().getClassLoader().getResource("magic_numbers.txt");
        assertNotNull(magicNumbersResource, "Файл magic_numbers.txt не найден в ресурсах");

        FileTypeDatabase database = new FileTypeDatabase(magicNumbersResource.getFile());
        FileAnalyzer analyzer = new FileAnalyzer(database);

        // Загружаем файл из ресурсов
        URL resource = getClass().getClassLoader().getResource("test.png");
        assertNotNull(resource, "Файл test.png не найден в ресурсах");
        File file = new File(resource.getFile());

        // Анализируем файл
        String extension = analyzer.analyzeFile(file);

        // Проверяем, что расширение корректно распознано
        assertEquals("png", extension, "Расширение файла должно быть png");
    }

    @Test
    public void testAnalyzePdfFile() throws IOException, UnsupportedFileFormatException {
        // Загружаем файл magic_numbers.txt из ресурсов
        URL magicNumbersResource = getClass().getClassLoader().getResource("magic_numbers.txt");
        assertNotNull(magicNumbersResource, "Файл magic_numbers.txt не найден в ресурсах");

        FileTypeDatabase database = new FileTypeDatabase(magicNumbersResource.getFile());
        FileAnalyzer analyzer = new FileAnalyzer(database);

        // Загружаем файл из ресурсов
        URL resource = getClass().getClassLoader().getResource("test.pdf");
        assertNotNull(resource, "Файл test.pdf не найден в ресурсах");
        File file = new File(resource.getFile());

        // Анализируем файл
        String extension = analyzer.analyzeFile(file);

        // Проверяем, что расширение корректно распознано
        assertEquals("pdf", extension, "Расширение файла должно быть pdf");
    }

    @Test
    public void testAnalyzeUnrecognizedFile() throws IOException {
        // Загружаем файл magic_numbers.txt из ресурсов
        URL magicNumbersResource = getClass().getClassLoader().getResource("magic_numbers.txt");
        assertNotNull(magicNumbersResource, "Файл magic_numbers.txt не найден в ресурсах");

        FileTypeDatabase database = new FileTypeDatabase(magicNumbersResource.getFile());
        FileAnalyzer analyzer = new FileAnalyzer(database);

        // Создаем временный файл с неизвестным форматом
        File file = File.createTempFile("unknown", ".tmp");
        file.deleteOnExit();
        System.out.println("Temporary file path: " + file.getAbsolutePath());
        // Проверяем, что исключение выбрасывается
        Exception exception = assertThrows(UnsupportedFileFormatException.class, () -> analyzer.analyzeFile(file));

        // Проверяем сообщение исключения
        assertTrue(exception.getMessage().contains("Unsupported file format"),
                "Сообщение исключения должно содержать 'Unsupported file format'");
    }
}