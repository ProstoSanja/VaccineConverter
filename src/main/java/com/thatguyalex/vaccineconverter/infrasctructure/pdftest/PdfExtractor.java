package com.thatguyalex.vaccineconverter.infrasctructure.pdftest;

import com.thatguyalex.vaccineconverter.domain.interfaces.BufferedImageCandidate;
import com.thatguyalex.vaccineconverter.infrasctructure.pdftest.classes.RenderablePdfImageCandidate;
import com.thatguyalex.vaccineconverter.infrasctructure.pdftest.classes.StaticPdfImageCandidate;
import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.PDFStreamEngine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.contentstream.operator.state.Concatenate;
import org.apache.pdfbox.contentstream.operator.state.Restore;
import org.apache.pdfbox.contentstream.operator.state.Save;
import org.apache.pdfbox.contentstream.operator.state.SetGraphicsStateParameters;
import org.apache.pdfbox.contentstream.operator.state.SetMatrix;

public class PdfExtractor extends PDFStreamEngine
{

    @SneakyThrows
    public PdfExtractor(InputStream pdfInputStream) {
        document = PDDocument.load(pdfInputStream);
        addOperator(new Concatenate());
        addOperator(new SetGraphicsStateParameters());
        addOperator(new Save());
        addOperator(new Restore());
        addOperator(new SetMatrix());
    }

    PDDocument document;
    private int currentPage = 0;
    private List<BufferedImageCandidate> foundImages = new ArrayList<>();

    @SneakyThrows
    public List<BufferedImageCandidate> getImages() {
        for (PDPage page : document.getPages()) {
            processPage(page);
            currentPage += 1;
        }

        foundImages.sort((i1, i2) -> {
            if (i1.getClass().equals(i2.getClass())) {
                return 0;
            } else if (i1 instanceof RenderablePdfImageCandidate) {
                return 1;
            }
            return -1;
        });

        if (foundImages.size() < 1) {
            throw new RuntimeException("No data found in PDF");
        }

        return foundImages;
    }

    @SneakyThrows
    public void dispose() {
        document.close();
    }

    @Override
    protected void processOperator( Operator operator, List<COSBase> operands) throws IOException
    {
        String operation = operator.getName();
        if( "Do".equals(operation) )
        {
            var object = getResources().getXObject((COSName) operands.get(0));
            if(object instanceof PDFormXObject xf)
            {
                Matrix ctmNew = getGraphicsState().getCurrentTransformationMatrix();

                var posX  = ctmNew.getTranslateX();
                var posY  = ctmNew.getTranslateY();

                var bbox = xf.getBBox();
                var sizeX = bbox.getWidth();
                var sizeY = bbox.getHeight();

                var qrRectangle = new PDRectangle(posX, posY, sizeX, sizeY);
                foundImages.add(new RenderablePdfImageCandidate(document, qrRectangle, currentPage));

            } else if (object instanceof PDImageXObject xi) {
                foundImages.add(new StaticPdfImageCandidate(xi.getImage()));
            }
        }
        else
        {
            super.processOperator( operator, operands);
        }
    }

}