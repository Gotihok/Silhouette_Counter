package com.shpp.p2p.cs.bhnatiuk.assignment13;

/**
 * Interface that stores constants required for work of the program.
 */
public interface Constants {
    /**
     * The path to folder with images.
     * <p>Set empty to get global access to the files</p>
     */
    String PATH_TO_IMAGE = "";

    /** Default file name if args is empty */
    String DEFAULT_FILENAME = "test.jpg";

    /** Maximal Euclidean distance of the color (inclusively) to be considered as background */
    int MAX_BACKGROUND_DIVERSITY = 20;

    /**
     * Constant that controls the minimal size of counted objects
     * (relative size counted from the biggest silhouette size)
     */
    double MIN_SILHOUETTE_SIZE_MULTIPLIER = 0.2;

    /** Constant that controls how many pixels will the erosion algorithm remove */
    double EROSION_MULTIPLIER = 0.00003;

    /** Markdown number of the background pixels */
    int BACKGROUND_NUM = 1;

    /** Markdown number of unchecked pixels */
    int UNCHECKED_PIXEL_NUM = 0;
}
