package com.shpp.p2p.cs.bhnatiuk.assignment13;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>Counts the silhouettes number on the image with monotonic-like background using BFS.</p>
 *
 * <p>The {@code filename} and the {@code path} of the image must be passed through the {@code args}
 * as one parameter (2nd and every next parameter will be ignored, ), otherwise the program
 * will try to read the file with the name and the path defined in constants.</p>
 *
 * <p>If the result don't matches with your needs, try to change
 * {@code MIN_SILHOUETTE_SIZE_MULTIPLIER} or {@code EROSION_MULTIPLIER} constant.</p>
 */
public class Assignment13Part1 {
    /**
     * <p>Main method that runs the program</p>
     * <p>Reads the filename from args or, if args is empty, from constant</p>
     * @param args Name of the image file (should contain file name or nothing)
     */
    public static void main(String[] args) {
        // Reading the file from the assets
        BufferedImage image = ImageReader.getImage(args);

        // Getting background color
        Color backgroundColor = BackgroundMask.getBackgroundColor(image);

        // Making contrast (background / not background) mask of image
        int[][] backgroundMask = BackgroundMask.makeBackgroundMask(image, backgroundColor);

        // Counting the silhouettes
        SilhouetteCounter counter = new SilhouetteCounter(backgroundMask);

        // Applying the erosion algorithm
        counter.applyErosion();

        // Counting the number of the silhouettes
        counter.findSilhouettes();

        // Subtracting small silhouettes
        int silhouettesNum = counter.subtractSmallSilhouettes();

        // Printing results into console
        System.out.println(silhouettesNum);
    }
}
