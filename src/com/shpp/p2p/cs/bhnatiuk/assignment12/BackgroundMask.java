package com.shpp.p2p.cs.bhnatiuk.assignment12;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for working with the background of the image
 */
public class BackgroundMask implements MarkdownConstants {
    /** Maximal Euclidean distance of the color (inclusively) to be considered as background */
    private static final int MAX_BACKGROUND_DIVERSITY = 20;

    /**
     * Calculates the average background colour
     * @param image Source image to get background color from
     * @return Color object that contains the average background colour
     */
    public static Color getBackgroundColor (BufferedImage image) {
        // HashMap of all colours of the image
        // Contains: color - number of pixel
        HashMap<Integer, Integer> countedColours = new HashMap<>();

        int width = image.getWidth();
        int height = image.getHeight();

        // Loop processes every pixel of the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);

                // Fills the hashMap with colors and their occurrences numbers
                countedColours.put(color, countedColours.getOrDefault(color, 1) + 1);
            }
        }

        // Getting most frequent color of the background from countedColours HashMap
        int mostFrequentColor = countedColours.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();

        return new Color(mostFrequentColor);
    }

    /**
     * Makes contrast (background / not background) mask of image
     * @param image Source image
     * @param backgroundColor Colour of the background of the image
     * @return short[][] array, as contrast (background / not background) mask of image
     */
    public static int[][] makeBackgroundMask (BufferedImage image, Color backgroundColor) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[][] backgroundMask = new int[height][width];

        // Getting each canal info from the background color
        int backR = backgroundColor.getRed();
        int backG = backgroundColor.getGreen();
        int backB = backgroundColor.getBlue();

        // Loop processes every pixel of image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Getting each canal info from a pixel color
                Color pixelColor = new Color(image.getRGB(x, y));
                int r = pixelColor.getRed();
                int g = pixelColor.getGreen();
                int b = pixelColor.getBlue();

                // Calculating the Euclidean distance between background color and the pixel's color
                double sqrDistance = (backR - r) * (backR - r) + (backG - g) * (backG - g) + (backB - b) * (backB - b);
                double distance = Math.sqrt(sqrDistance);

                // Writing info to the mask
                if (distance <= MAX_BACKGROUND_DIVERSITY) {
                    backgroundMask[y][x] = BACKGROUND_NUM;
                } else {
                    backgroundMask[y][x] = UNCHECKED_PIXEL_NUM;
                }
            }
        }

        return backgroundMask;
    }
}
