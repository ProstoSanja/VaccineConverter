package com.thatguyalex.vaccineconverter.infrasctructure.utils;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageUtils {

    @SneakyThrows
    public static BufferedImage saveImage(BufferedImage image) {
        ImageIO.write(image, "png", new File(image.hashCode() + ".png"));
        return image;
    }

}
