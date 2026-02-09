package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TimeLogger {

    private static final String FILE = "times.csv";

    public static void log(String algorithm, long timeNs) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE, true))) {

            // Si el archivo está vacío, escribir encabezado
            if (new java.io.File(FILE).length() == 0) {
                writer.write("algoritmo,tiempo_ns");
                writer.newLine();
            }

            writer.write(algorithm + "," + timeNs);
            writer.newLine();

        } catch (IOException e) {
            System.err.println("Error escribiendo archivo CSV: " + e.getMessage());
        }
    }
}

