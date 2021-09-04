package com.thatguyalex.vaccineconverter.infrasctructure.pdftest.classes;

import com.thatguyalex.vaccineconverter.domain.interfaces.BufferedImageCandidate;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;

@AllArgsConstructor
public class RenderablePdfImageCandidate implements BufferedImageCandidate {

    PDDocument document;
    PDRectangle cropBox;
    int pageIndex;

    @Override
    @SneakyThrows
    public BufferedImage getImage() {
        document.getPage(pageIndex).setCropBox(cropBox);
        return new PDFRenderer(document).renderImageWithDPI(pageIndex, 600);
    }

}
