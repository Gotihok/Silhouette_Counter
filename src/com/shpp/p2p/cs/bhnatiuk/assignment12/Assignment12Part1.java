package com.shpp.p2p.cs.bhnatiuk.assignment12;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * <p>Counts the silhouettes number on the image with monotonic-like background using DFS.</p>
 *
 * <p>The {@code filename} and the {@code path} of the image must be passed through the {@code args}
 * as one parameter (2nd and every next parameter will be ignored, ), otherwise the program
 * will try to read the file with the name and the path defined in constants.</p>
 *
 * <p>If the result don't matches with your needs,
 * try to change the {@code MIN_SILHOUETTE_SIZE_MULTIPLIER} constant.</p>
 *
 * <p>Consider increasing the stack size to prevent {@link StackOverflowError}</p>
 */
public class Assignment12Part1 {
    /**
     * The path to folder with images.
     * <p>Set empty to get global access to the files</p>
     */
    public static final String PATH_TO_IMAGE = "";

    /** Default file name if args is empty */
    public static final String DEFAULT_FILENAME = "test.jpg";

    /** Constant that controls the minimal size (relative size counted from the whole image size) of counted objects */
    private static final double MIN_SILHOUETTE_SIZE_MULTIPLIER = 0.03;

    /**
     * <p>Main method that runs the program</p>
     * <p>Reads the filename from args or, if args is empty, from constant</p>
     * @param args Name of the image file (should contain file name or nothing)
     */
    public static void main(String[] args) {
        // Reading the file from the assets
        String filename;
        if (args.length == 0) {
            filename = DEFAULT_FILENAME;
        } else {
            filename = args[0];
        }
        BufferedImage image = getImage(PATH_TO_IMAGE + filename);

        // Getting background color
        Color backgroundColor = BackgroundMask.getBackgroundColor(image);
        System.out.println(backgroundColor);

        // Making contrast (background / not background) mask of image
        int[][] backgroundMask = BackgroundMask.makeBackgroundMask(image, backgroundColor);

        // Counting the silhouettes
        int silhouettesNum = getSilhouettesNum(backgroundMask, image);

        // Printing results into console
        System.out.println(silhouettesNum);
    }

    /**
     * Counts the silhouettes number using contrast image (background/silhouette).
     *
     * @param backgroundMask The contrast mask if the image.
     * @param image Processed image
     * @return Number of the silhouettes without subtracted small objects.
     */
    private static int getSilhouettesNum(int[][] backgroundMask, BufferedImage image) {
        SilhouetteCounter counter = new SilhouetteCounter(backgroundMask);
        int silhouettesNum = counter.findSilhouettes();

        // Subtracting small silhouettes
        int minSilhouetteSize = (int) (image.getWidth() * image.getHeight() * MIN_SILHOUETTE_SIZE_MULTIPLIER);
        HashMap<Integer, Integer> areas = counter.getAreasOfSilhouettes();
        for (int key : areas.keySet()) {
            if (areas.get(key) < minSilhouetteSize) {
                silhouettesNum--;
            }
        }

        return silhouettesNum;
    }

    /**
     * <p>Reads the image in specified place</p>
     * <p>Logs "Couldn't read the image" into console if didn't read the image</p>
     * @param path Place of the image (path to directory + file name)
     * @return BufferedImage of read file or null if it hadn't been read
     */
    private static BufferedImage getImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Couldn't read the image");
        }

        return image;
    }
}
