package com.fileanalyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс для логирования сообщений в приложении.
 * Использует библиотеку SLF4J для записи логов разного уровня:
 * - Информационные сообщения (info)
 * - Предупреждения (warn)
 * - Ошибки (error)
 * Этот класс предоставляет статические методы для удобного вызова логирования
 * из любого места приложения.
 */
public class AppLogger {

    // Логгер, используемый для записи сообщений
    private static final Logger logger = LoggerFactory.getLogger(AppLogger.class);

    /**
     * Логирует информационное сообщение.
     *
     * @param message Сообщение, которое необходимо залогировать.
     */
    public static void info(String message) {
        logger.info(message);
    }

    /**
     * Логирует сообщение об ошибке с указанием исключения.
     *
     * @param message   Сообщение, описывающее ошибку.
     * @param throwable Исключение, связанное с ошибкой.
     */
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Логирует предупреждающее сообщение.
     *
     * @param message Сообщение, которое необходимо залогировать как предупреждение.
     */
    public static void warn(String message) {
        logger.warn(message);
    }
}