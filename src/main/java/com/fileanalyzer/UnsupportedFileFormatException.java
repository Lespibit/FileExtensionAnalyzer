package com.fileanalyzer;

/**
 * Исключение, выбрасываемое при попытке работы с файлом неподдерживаемого формата.
 * Это исключение используется для указания на то, что формат файла не соответствует
 * ожидаемому или допустимому в контексте приложения.
 *
 * Пример использования:
 * <pre>
 *     if (!file.getName().endsWith(".txt")) {
 *         throw new UnsupportedFileFormatException("Файл должен быть в формате .txt");
 *     }
 * </pre>
 */
public class UnsupportedFileFormatException extends Exception {

    /**
     * Создает новое исключение с указанным сообщением.
     *
     * @param message Сообщение, описывающее причину возникновения исключения.
     *                Это сообщение может быть использовано для информирования пользователя
     *                или логирования ошибки.
     */
    public UnsupportedFileFormatException(String message) {
        super(message);
    }
}