package persistencia;

import java.io.*;
import java.util.*;

public class DataManagerText {
    private final File file;
    private static final String SEP = ";";
    

    public DataManagerText(String filepath) {
        this.file = new File(filepath);
    }

    public List<String[]> loadData() throws IOException {
        List<String[]> rows = new ArrayList<>();
        if (!file.exists()) return rows;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                rows.add(line.split(SEP));
            }
        }
        return rows;
    }

    public void guardarDatos(List<String[]> rows) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String[] row : rows) {
                bw.write(String.join(SEP, row));
                bw.newLine();
            }
        }
    }
}