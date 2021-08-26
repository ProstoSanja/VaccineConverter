package com.thatguyalex.vaccineconverter.infrasctructure.greenpass;


import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.thatguyalex.vaccineconverter.domain.model.GreenPass;
import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.web.multipart.MultipartFile;
import se.digg.dgc.service.DGCDecoder;
import se.digg.dgc.service.impl.DefaultDGCDecoder;
import se.digg.dgc.signatures.impl.DefaultDGCSignatureVerifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GreenPassRepository {

    private final DGCDecoder dgcDecoder;
    private final GreenPassNameProvider greenPassNameProvider;

    public GreenPassRepository(GreenPassCertificateProvider greenPassCertificateProvider, GreenPassNameProvider greenPassNameProvider) {
        this.dgcDecoder = new DefaultDGCDecoder(new DefaultDGCSignatureVerifier(), greenPassCertificateProvider);
        this.greenPassNameProvider = greenPassNameProvider;
    }

    public GreenPass parseGreenPassFromPdf(MultipartFile file) {
        return parseGreenPass(extractImageFromPdf(file));
    }

    @SneakyThrows
    public GreenPass parseGreenPassFromImage(MultipartFile file) {
        return parseGreenPass(ImageIO.read(file.getInputStream()));
    }

    @SneakyThrows
    private GreenPass parseGreenPass(BufferedImage image) {
        var extractedPayload = decodeQr(image);

        var greenCertificate = dgcDecoder.decode(extractedPayload);

        var greenCertificateId = greenCertificate.getV().get(0).getCi();
        var greenCertificateNiceId = greenCertificateId.replaceAll("(\\:|\\#|\\/)", "");

        return GreenPass.builder()
                .dataPayload(extractedPayload)
                .firstName(greenCertificate.getNam().getGn())
                .lastName(greenCertificate.getNam().getFn())
                .dateOfBirth(greenCertificate.getDob())
                .dosesNeeded(greenCertificate.getV().get(0).getSd().toString())
                .dosesGiven(greenCertificate.getV().get(0).getDn().toString())
                .dateOfPass(greenCertificate.getV().get(0).getDt().toString())
                .vaccineType(greenPassNameProvider.getNameForVaccine(greenCertificate.getV().get(0).getMp()))
                .dataId(greenCertificateId)
                .dataNiceId(greenCertificateNiceId)
                .build();
    }

    private String decodeQr(BufferedImage image) {
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new QRCodeReader().decode(bitmap);
            return result.getText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse QR");
        }
    }

    @SneakyThrows
    private BufferedImage extractImageFromPdf(MultipartFile file) {
        List<BufferedImage> foundImages = new ArrayList<>();

        PDDocument document = PDDocument.load(file.getInputStream());
        for (PDPage page : document.getPages()) {
            PDResources pdResources = page.getResources();
            for (COSName c : pdResources.getXObjectNames()) {
                PDXObject o = pdResources.getXObject(c);
                if (o instanceof PDImageXObject oi) {
                    foundImages.add(oi.getImage());
                }
            }
        }
        document.close();

        return switch (foundImages.size()) {
            case 0 -> throw new RuntimeException("No images found");
            case 1 -> foundImages.get(0);
            default -> throw new RuntimeException("Wrong file?");
        };

    }

}
