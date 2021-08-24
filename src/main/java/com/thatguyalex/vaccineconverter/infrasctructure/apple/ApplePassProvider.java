package com.thatguyalex.vaccineconverter.infrasctructure.apple;

import com.thatguyalex.vaccineconverter.domain.model.GreenPass;
import de.brendamour.jpasskit.PKBarcode;
import de.brendamour.jpasskit.PKField;
import de.brendamour.jpasskit.PKPass;
import de.brendamour.jpasskit.enums.PKBarcodeFormat;
import de.brendamour.jpasskit.enums.PKPassType;
import de.brendamour.jpasskit.enums.PKTextAlignment;
import de.brendamour.jpasskit.passes.PKGenericPass;
import de.brendamour.jpasskit.signing.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;


@Slf4j
public class ApplePassProvider {

    private final PKSigningInformation pkSigningInformation;
    private final PKPassTemplateInMemory pkPassTemplateInMemory;


    private final HashMap<String, ApplePassInMemory> passStorage = new HashMap<>();

    @SneakyThrows
    public ApplePassProvider() {
        var appCert = this.getClass().getResourceAsStream("/credentials/applecert.p12");
        var pass = new String(this.getClass().getResourceAsStream("/credentials/applecert.txt").readAllBytes()).strip();
        var appleCert = this.getClass().getResourceAsStream("/credentials/AppleWWDRCA.cer");

        pkSigningInformation = new PKSigningInformationUtil().loadSigningInformationFromPKCS12AndIntermediateCertificate(
                appCert,
                pass,
                appleCert);

        var logo = this.getClass().getResourceAsStream("/images/icon.jpg");
        var logoRetina = this.getClass().getResourceAsStream("/images/icon@2x.jpg");

        pkPassTemplateInMemory = new PKPassTemplateInMemory();
        pkPassTemplateInMemory.addFile(PKPassTemplateInMemory.PK_ICON, logo);
        pkPassTemplateInMemory.addFile(PKPassTemplateInMemory.PK_ICON_RETINA, logoRetina);

         logo = this.getClass().getResourceAsStream("/images/icon.jpg");
         logoRetina = this.getClass().getResourceAsStream("/images/icon@2x.jpg");
        pkPassTemplateInMemory.addFile(PKPassTemplateInMemory.PK_LOGO, logo);
        pkPassTemplateInMemory.addFile(PKPassTemplateInMemory.PK_LOGO_RETINA, logoRetina);


    }

    @SneakyThrows
    public void generateApplePass(GreenPass greenPass) {
        PKPass pass = PKPass.builder()
                .pass(
                        PKGenericPass.builder()
                                .passType(PKPassType.PKGenericPass)
                                .primaryFieldBuilder(PKField.builder()
                                        .key("title")
                                        .value("European Health Certificate")
                                )
                                .secondaryFields(
                                        List.of(
                                            PKField.builder()
                                                    .key("name")
                                                    .label("Given name")
                                                    .value(greenPass.getFirstName() + " " + greenPass.getLastName())
                                                    .textAlignment(PKTextAlignment.PKTextAlignmentLeft)
                                                    .build(),
                                            PKField.builder()
                                                    .key("dob")
                                                    .label("Date of Birth")
                                                    .value(greenPass.getDateOfBirth())
                                                    .textAlignment(PKTextAlignment.PKTextAlignmentRight)
                                                    .build()
                                ))
                                .auxiliaryFields(
                                        List.of(
                                                PKField.builder()
                                                        .key("vaxType")
                                                        .label("Vaccine name")
                                                        .value(greenPass.getVaccineType())
                                                        .textAlignment(PKTextAlignment.PKTextAlignmentLeft)
                                                        .build(),
                                                PKField.builder()
                                                        .key("vaxDoses")
                                                        .label("Doses given")
                                                        .value(greenPass.getDosesGiven() + "/" + greenPass.getDosesNeeded())
                                                        .textAlignment(PKTextAlignment.PKTextAlignmentCenter)
                                                        .build(),
                                                PKField.builder()
                                                        .key("dov")
                                                        .label("Vaccination Date")
                                                        .value(greenPass.getDateOfPass())
                                                        .textAlignment(PKTextAlignment.PKTextAlignmentRight)
                                                        .build()
                                        )
                                )
                )
                .barcodeBuilder(
                        PKBarcode.builder()
                                .format(PKBarcodeFormat.PKBarcodeFormatQR)
                                .message(greenPass.getDataPayload())
                                .altText(greenPass.getDataId())
                                .messageEncoding(StandardCharsets.UTF_8)
                )
                .formatVersion(1)
                .passTypeIdentifier("pass.com.thatguyalex.vaccineconverter")
                .groupingIdentifier("pass.com.thatguyalex.vaccineconverter")
                .serialNumber(greenPass.getDataNiceId())
                .teamIdentifier("8275GSHA89")
                .organizationName("Aleksandr Tsernoh")
                .logoText("Estonian Health information system")
                .description("European Vaccine certificate")
                .backgroundColor(Color.WHITE)
                .foregroundColor(Color.BLACK)
                .labelColor(Color.BLACK)
                .build();

        byte[] signedAndZippedPkPassArchive = new PKFileBasedSigningUtil().createSignedAndZippedPkPassArchive(pass, pkPassTemplateInMemory, pkSigningInformation);

        passStorage.put(greenPass.getDataNiceId(), new ApplePassInMemory(signedAndZippedPkPassArchive));

        greenPass.setApplePayLink("/apple/" + greenPass.getDataNiceId());
    }


    public byte[] getApplePass(String id) {
        try {
            return passStorage.get(id).getData();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find Apple pass with id: " + id, e);
        }
    }

    @Scheduled(cron = "* * 0 * * *")
    private void cleanupCache() {
        log.warn("Cleaning up apple Pay cache");
        var now = Instant.now();
        passStorage.forEach((niceId, value) -> {
            if (value.getExpirationTime().isAfter(now)) {
                passStorage.remove(niceId);
            }
        });
    }

}
