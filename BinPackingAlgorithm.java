import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Bin {
    int capacity;
    List<Integer> items;

    public Bin(int capacity) {
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    public boolean canAdd(int itemSize) {
        return itemSize <= capacity;
    }

    public boolean add(int itemSize) {
        if (canAdd(itemSize)) {
            items.add(itemSize);
            capacity -= itemSize;
            return true;
        }
        return false;
    }
}

public class BinPackingAlgorithm {
    public static List<Bin> binPacking(int[] itemSizes, int binCapacity) {
        List<Bin> bins = new ArrayList<>();
        for (int itemSize : itemSizes) {
            boolean added = false;
            for (Bin bin : bins) {
                if (bin.add(itemSize)) {
                    added = true;
                    break;
                }
            }
            if (!added) {
                Bin newBin = new Bin(binCapacity);
                newBin.add(itemSize);
                bins.add(newBin);
            }
        }
        return bins;
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("binpack1.txt"));
            int binCapacity = scanner.nextInt();
            List<Integer> itemSizes = new ArrayList<>();

            while (scanner.hasNext()) {
                itemSizes.add(scanner.nextInt());
            }

            List<Bin> bins = binPacking(itemSizes.stream().mapToInt(Integer::intValue).toArray(), binCapacity);

            System.out.println("Rozlokowanie przedmiotów:");
            for (int i = 0; i < bins.size(); i++) {
                System.out.print("Pojemnik " + (i + 1) + ": ");
                for (int itemSize : bins.get(i).items) {
                    System.out.print(itemSize + " ");
                }
                System.out.println();
            }

            System.out.println("Liczba wykorzystanych pojemników: " + bins.size());

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku.");
        }
    }
}
