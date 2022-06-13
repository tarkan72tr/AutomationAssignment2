import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utility {


    public static List<String[]> readFromCSV(String value) throws FileNotFoundException {
        List<String[]> listFake = new ArrayList<>();

        try {


            BufferedReader br = new BufferedReader(new FileReader(value));
            String conv;
            while ((conv = br.readLine()) != null) {
                String[] each = conv.split(",");
                listFake.add(each);

            }
        } catch (IOException e) {
            System.out.println("Exception");

        }
        return listFake;

    }
    public static StringBuilder generateCard(int select) {
        switch (select) {
            case (0):
                StringBuilder addVisa= new StringBuilder("4");

                for (int i = 0; i<15; i++)
                    addVisa.append((int) (Math.random() * 10));
                return addVisa;

            case(1):
                StringBuilder addMaster= new StringBuilder("5");

                for (int i = 0; i<15; i++)
                    addMaster.append((int) (Math.random() * 10));
                return addMaster;

            case(2):
                StringBuilder addAmerican= new StringBuilder("3");

                for (int i = 0; i<14; i++)
                    addAmerican.append((int) (Math.random() * 10));

                return addAmerican;

                    }

        return null;
    }
}
