package com.thatguyalex.vaccineconverter.infrasctructure.pdftest.classes;

import com.thatguyalex.vaccineconverter.domain.interfaces.BufferedImageCandidate;
import lombok.AllArgsConstructor;

import java.awt.image.BufferedImage;

@AllArgsConstructor
public class StaticPdfImageCandidate implements BufferedImageCandidate {

    private BufferedImage image;

    @Override
    public BufferedImage getImage() {
        return image;
    }
}
