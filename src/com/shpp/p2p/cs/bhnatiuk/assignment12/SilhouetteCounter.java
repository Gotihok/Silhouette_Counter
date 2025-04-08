package com.shpp.p2p.cs.bhnatiuk.assignment12;

import java.util.HashMap;

/**
 * SilhouetteCounter class is made for counting silhouettes
 * in the contrast image
 */
public class SilhouetteCounter implements MarkdownConstants {
    /** Image to make operations with */
    private final int[][] image;

    /** Size of the image */
    private final int width;
    private final int height;

    /** Areas of the silhouettes */
    private final HashMap<Integer, Integer> areaOfSilhouettes;

    /** Number of silhouettes in the image */
    int silhouettesCount;

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
    }

    /**
     * Counts the number of the silhouettes of the image array,
     * that is in the 'image' field of the object
     * @return The number of the silhouettes
     */
    public int findSilhouettes() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                checkPixel(true, x, y);
            }
        }

        return silhouettesCount;
    }

    /**
     * <p>Checks if current pixel is the silhouette's pixel, counts silhouettes area,
     * writes all the info to the object fields</p>
     * <p>If true - calls itself recursively (check all 8 pixels around), increases (+1) area of the silhouette</p>
     * <p>If false - returns 0 and ands work</p>
     * @param isFirstCall Defines if this method wasn't called recursively (false - recursively)
     * @param x The X coordinate of the pixel to check
     * @param y The Y coordinate of the pixel to check
     * @return Area of the silhouette or part of it that was returned by recursive calls
     */
    private int checkPixel(boolean isFirstCall, int x, int y) {
        // Area of current silhouette
        int area = 0;

        // Defines if this pixel was first of the silhouette - true
        // or was called recursively / is background - false
        boolean wasNewSilhouette = false;

        // Checking out of bounds
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1)
            return 0;

        // Checking if is the first checked pixel of the silhouette
        if (isFirstCall && image[y][x] == 0) {
            silhouettesCount++;
            wasNewSilhouette = true;
        }

        // If isn't the background calls itself recursively
        if (image[y][x] == 0) {
            area++;
            image[y][x] = silhouettesCount + 1;

            area += checkPixel(false, x - 1, y - 1);
            area += checkPixel(false, x, y - 1);
            area += checkPixel(false, x + 1, y - 1);
            area += checkPixel(false, x + 1, y);
            area += checkPixel(false, x + 1, y + 1);
            area += checkPixel(false, x, y + 1);
            area += checkPixel(false, x - 1, y + 1);
            area += checkPixel(false, x - 1, y);
        }

        // Adding the silhouette area to the HashMap starting from the 2.
        if (wasNewSilhouette) {
            areaOfSilhouettes.put(silhouettesCount + 1, area);
        }

        return area;
    }

    /**
     * Prints the processing image current state into console.
     */
    public void logIntoConsole () {
        for (int[] row : image) {
            for (int pixel : row) {
                if (pixel == BACKGROUND_NUM)
                    System.out.print(" ");
                else
                    System.out.print(pixel);
            }
            System.out.println();
        }
    }

    public HashMap<Integer, Integer> getAreasOfSilhouettes() {
        return areaOfSilhouettes;
    }
}
