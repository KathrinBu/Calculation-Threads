
import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
В директории лежат входные текстовые файлы, названы по такому правилу: in_<N>.dat, где N - натуральное
число. Каждый файл состоит из двух строк. В первой строке - число, обозначающее действие,
а во второй - числа с плавающей точкой, разделенные пробелом.
Действия могут быть следующими:
1 - сложение
2 - умножение
3 - сумма квадратов
Необходимо написать многопоточное приложение, которое выполнит требуемые
действия над числами и сумму результатов запишет в файл out.dat.
 */

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        try (FileWriter fileWriter = new FileWriter("out.dat")) {
            for (int fileNumber = 1; fileExists(fileNumber); fileNumber++) {
                // Чтение входного файла
                try (BufferedReader reader = new BufferedReader(new FileReader("in_" + fileNumber + ".dat"))) {
                    // Чтение действия
                    int action = Integer.parseInt(reader.readLine());
                    // Чтение чисел
                    String[] numbers = reader.readLine().split(" ");

                    // Создание и запуск потока для выполнения действия
                    Future<Double> result = executor.submit(new Task(action, numbers));
                    fileWriter.write("Результат " + fileNumber + ": " + result.get() + "\n"); // Запись результата в файл
                } catch (IOException | NumberFormatException | ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        executor.shutdown(); // Завершаем работу пула потоков
    }

    // Проверка существования входного файла
    private static boolean fileExists(int fileNumber) {
        File file = new File("in_" + fileNumber + ".dat");
        return file.exists() && file.isFile();
    }
}