import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class FileReader {
    ArrayList<String> readFileContents(String fileName) {
        String path = "./resources/" + fileName;
        try {
            Main.error = false;
            if (!Main.strings2.contains(fileName))
            Main.strings2.add(fileName);
            else {
                Main.error = true;
                System.out.println("Этот файл уже был считан и обработан в объект программы !\n");
            }
                return new ArrayList<>(Files.readAllLines(Path.of(path)));

        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с отчётом. Возможно, файл отсутствует в нужной директории.");
            Main.error = true;
            return new ArrayList<>();
        }
    }

}
