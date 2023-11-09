import java.util.concurrent.Callable;

public class Task implements Callable<Double> {
    private final int action;
    private final String[] numbers;

    public Task(int action, String[] numbers) {
        this.action = action;
        this.numbers = numbers;
    }

    @Override
    public Double call() {
        double result = 0.0;
        switch (action){
            case 1: //сложение
                for (String s:numbers) {
                    result+=Double.parseDouble(s);
                }
                break;
            case 2: // Умножение
                for (String s:numbers) {
                    result*=Double.parseDouble(s);
                }
                break;
            case 3: // Сумма квадратов
                for (String s:numbers) {
                    double num=Double.parseDouble(s);
                    result+=num*num;
                }
                break;
        }
        return result;
    }
}
