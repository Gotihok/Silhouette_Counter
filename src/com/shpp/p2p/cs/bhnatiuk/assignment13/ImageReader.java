package com.shpp.p2p.cs.bhnatiuk.assignment13;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class that handles the file reading
 */
public class ImageReader implements Constants {
    /**
     * Handles file name and directory and reads image with calculated parameters
     * @param args String array with the arguments from the main program where should be the filename
     * @return BufferedImage object of read image
     * @throws NullPointerException If didn't read the image or something went wrong
     *                              with the image data and it is null
     */
    public static BufferedImage getImage (String[] args) {
        String filename;
        if (args.length == 0) {
            filename = DEFAULT_FILENAME;
        } else {
            filename = args[0];
        }
        BufferedImage image = readImage(PATH_TO_IMAGE + filename);

        if (image == null) {
            throw new NullPointerException("File is null. Possible reason: wrong file name");
        }

        return image;
    }

    /**
     * <p>Reads the image in specified place</p>
     * <p>Logs "Couldn't read the image" into console if didn't read the image</p>
     * @param path Place of the image (path to directory + file name)
     * @return BufferedImage of read file or null if it hadn't been read
     */
    private static BufferedImage readImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Couldn't read the image");
        }

        return image;
    }
}
