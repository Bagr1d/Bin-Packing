import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Bins {
    int capacity;
    List<Integer> items;

    public Bins(int capacity) {
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

public class BinPackingAlgorithms {
    public static List<Bin> nextFit(int[] itemSizes, int binCapacity) {
        List<Bin> bins = new ArrayList<>();
        Bin currentBin = new Bin(binCapacity);

        for (int itemSize : itemSizes) {
            if (!currentBin.add(itemSize)) {
                bins.add(currentBin);
                currentBin = new Bin(binCapacity);
                currentBin.add(itemSize);
            }
        }

        bins.add(currentBin);
        return bins;
    }

    public static List<Bin> firstFit(int[] itemSizes, int binCapacity) {
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

    public static List<Bin> bestFit(int[] itemSizes, int binCapacity) {
        List<Bin> bins = new ArrayList();

        for (int itemSize : itemSizes) {
            Bin bestBin = null;
            int bestCapacity = Integer.MAX_VALUE;

            for (Bin bin : bins) {
                if (bin.canAdd(itemSize) && bin.capacity - itemSize < bestCapacity) {
                    bestBin = bin;
                    bestCapacity = bin.capacity - itemSize;
                }
            }

            if (bestBin != null) {
                bestBin.add(itemSize);
            } else {
                Bin newBin = new Bin(binCapacity);
                newBin.add(itemSize);
                bins.add(newBin);
            }
        }

        return bins;
    }

    public static List<Bin> worstFit(int[] itemSizes, int binCapacity) {
        List<Bin> bins = new ArrayList();

        for (int itemSize : itemSizes) {
            Bin worstBin = null;
            int worstCapacity = binCapacity;

            for (Bin bin : bins) {
                if (bin.canAdd(itemSize) && bin.capacity < worstCapacity) {
                    worstBin = bin;
                    worstCapacity = bin.capacity;
                }
            }

            if (worstBin != null) {
                worstBin.add(itemSize);
            } else {
                Bin newBin = new Bin(binCapacity);
                newBin.add(itemSize);
                bins.add(newBin);
            }
        }

        return bins;
    }

    public static List<Bin> firstFitDecreasing(int[] itemSizes, int binCapacity) {
        int[] sortedItemSizes = Arrays.stream(itemSizes).boxed()
                .sorted((a, b) -> Integer.compare(b, a))
                .mapToInt(Integer::intValue)
                .toArray();
        return firstFit(sortedItemSizes, binCapacity);
    }

    public static int calculateNumberOfBins(List<Bin> bins) {
        return bins.size() - 1;
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("binpack1.txt"));
            int binCapacity = scanner.nextInt();
            List<Integer> itemSizes = new ArrayList<>();

            while (scanner.hasNext()) {
                itemSizes.add(scanner.nextInt());
            }

            int[] itemSizesArray = itemSizes.stream().mapToInt(Integer::intValue).toArray();

            List<Bin> nextFitBins = nextFit(itemSizesArray, binCapacity);
            List<Bin> firstFitBins = firstFit(itemSizesArray, binCapacity);
            List<Bin> bestFitBins = bestFit(itemSizesArray, binCapacity);
            List<Bin> worstFitBins = worstFit(itemSizesArray, binCapacity);
            List<Bin> firstFitDecreasingBins = firstFitDecreasing(itemSizesArray, binCapacity);

            System.out.println("Liczba wykorzystanych pojemników (Next-Fit): " + calculateNumberOfBins(nextFitBins));
            System.out.println("Liczba wykorzystanych pojemników (First-Fit): " + calculateNumberOfBins(firstFitBins));
            System.out.println("Liczba wykorzystanych pojemników (Best-Fit): " + calculateNumberOfBins(bestFitBins));
            System.out.println("Liczba wykorzystanych pojemników (Worst-Fit): " + calculateNumberOfBins(worstFitBins));
            System.out.println("Liczba wykorzystanych pojemników (First-Fit Decreasing): " + calculateNumberOfBins(firstFitDecreasingBins));

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku.");
        }
    }
}
