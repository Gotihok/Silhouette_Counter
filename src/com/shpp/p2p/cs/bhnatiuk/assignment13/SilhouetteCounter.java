package com.shpp.p2p.cs.bhnatiuk.assignment13;

import java.util.*;

/**
 * SilhouetteCounter class is made for counting silhouettes
 * in the contrast image
 */
public class SilhouetteCounter implements Constants {
    /** Image to make operations with */
    private final int[][] image;

    /** Size of the image */
    private final int width;
    private final int height;

    /** Areas of the silhouettes */
    private final HashMap<Integer, Integer> areaOfSilhouettes;

    /** Number of silhouettes in the image */
    int silhouettesCount;

    int layersForErosion;

    /**
     * Constructor of the SilhouetteCounter object
     * @param image Image to make operations with
     */
    public SilhouetteCounter (int[][] image) {
        this.image = image;
        silhouettesCount = 0;
        width = image[0].length;
        height = image.length;
        areaOfSilhouettes = new HashMap<>();
        layersForErosion = (int) (width * height * EROSION_MULTIPLIER);
//        System.out.println("layersForErosion = " + layersForErosion);
    }

    /**
     * <p>Applies the erosion algorithm on the image</p>
     * <p>The algorithm removes (marks negative and starts with -2) layers of the bounds of silhouettes in the image</p>
     */
    public void applyErosion () {
        for (int i = 0; i < layersForErosion; i++) {
            applyErosionLayer(i);
        }
    }

    /**
     * Applies the erosion algorithm on the layer
     * @param iteration The iteration of the algorithm running
     */
    private void applyErosionLayer(int iteration) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Checking if pixel is silhouette
                if (image[y][x] != 0) continue;

                applyPixelErosion(x, y, -iteration - 2);
            }
        }
    }

    /**
     * Marks pixel as negative with the ID,
     * if it bounds with the background or previous layer of the erosion
     * @param x The X coordinate of the pixel to check
     * @param y The Y coordinate of the pixel to check
     * @param ID The ID of the pixel to mark
     */
    private void applyPixelErosion(int x, int y, int ID) {
        // Checks bottom pixel
        if (y - 1 >= 0 && (image[y - 1][x] == 1 || image[y - 1][x] == ID + 1)) {
            image[y][x] = ID;
        }

        // Checks top pixel
        if (y + 1 < height && (image[y + 1][x] == 1 || image[y + 1][x] == ID + 1)) {
            image[y][x] = ID;
        }

        // Checks left pixel
        if (x - 1 >= 0 && (image[y][x - 1] == 1 || image[y][x - 1] == ID + 1)) {
            image[y][x] = ID;
        }

        // Checks right pixel
        if (x + 1 < width && (image[y][x + 1] == 1 || image[y][x + 1] == ID + 1)) {
            image[y][x] = ID;
        }
    }

    /**
     * Subtracts smaller silhouettes than the biggest * constant
     * @return number of the silhouettes after subtraction
     */
    public int subtractSmallSilhouettes() {
        int biggestSilhouetteArea;

        // Getting the biggest area from HashMap
        Optional<Map.Entry<Integer, Integer>> biggestSilhouetteAreaOptional =
                areaOfSilhouettes.entrySet().stream().max(Map.Entry.comparingByValue());

        if (biggestSilhouetteAreaOptional.isPresent()) {
            biggestSilhouetteArea = biggestSilhouetteAreaOptional.get().getValue();
        } else {
            return 0;
        }

        int minSilhouetteSize = (int) (biggestSilhouetteArea * MIN_SILHOUETTE_SIZE_MULTIPLIER);

        for (int key : areaOfSilhouettes.keySet()) {
            if (areaOfSilhouettes.get(key) < minSilhouetteSize) {
                silhouettesCount--;
            }
        }

        return silhouettesCount;
    }

    /**
     * Counts the number of the silhouettes of the image array,
     * that is in the 'image' field of the object
     * @return The number of the silhouettes
     */
    public int findSilhouettes() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (image[y][x] == 0) {
                    breadthFirstSearch(x, y);
                }
            }
        }

        return silhouettesCount;
    }

    /**
     * <p>Checks if current pixel is the silhouette's pixel, counts silhouettes area,
     * writes all the info to the object fields</p>
     * <p>If true - calls itself recursively (check all 8 pixels around), increases (+1) area of the silhouette</p>
     * <p>If false - returns 0 and ands work</p>
     * @param x The X coordinate of the pixel to check
     * @param y The Y coordinate of the pixel to check
     */
    private void breadthFirstSearch(int x, int y) {
        Queue<Pixel> pixelsToCheck = new LinkedList<>();

        pixelsToCheck.add(new Pixel(x, y));
        silhouettesCount++;
        int silhouetteArea = 1;

        while (!pixelsToCheck.isEmpty()) {
            Pixel current = pixelsToCheck.poll();
            silhouetteArea++;

            addPixelsToCheck(pixelsToCheck, current.getX(), current.getY());
        }

        areaOfSilhouettes.put(silhouettesCount, silhouetteArea);
    }

    /**
     * Adds pixels to the queue to check in the breadthFirstSearch()
     * @param pixelsToCheck Queue link where to write the info
     * @param x The X coordinate of the pixel to check
     * @param y The Y coordinate of the pixel to check
     */
    private void addPixelsToCheck(Queue<Pixel> pixelsToCheck, int x, int y) {
        for (int row = y - 1; row <= y + 1; row++) {
            for (int col = x - 1; col <= x + 1; col++) {
                if (col < 0 || col > width - 1 || row < 0 || row > height - 1) continue;

                if (image[row][col] == 0) {
                    pixelsToCheck.add(new Pixel(col, row));
                    image[row][col] = silhouettesCount + 1;
                }
            }
        }
    }

    /**
     * Prints the processing image current state into console.
     */
    public void logIntoConsole () {
        for (int[] in : image) {
            for (int i : in) {
                if (i == 1)
                    System.out.print("   ");
                else if (i > -10 && i < 0 || i >= 10)
                    System.out.print(" " + i);
                else if (i <= -10)
                    System.out.print(i);
                else
                    System.out.print("  " + i);
            }
            System.out.println();
        }
    }
}
