package com.thatguyalex.vaccineconverter.infrasctructure.google.passes;

import com.google.api.services.walletobjects.model.*;
import com.thatguyalex.vaccineconverter.domain.interfaces.IntegratedPassProvider;
import com.thatguyalex.vaccineconverter.domain.model.GreenPass;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class GoogleLoyaltyRepository implements IntegratedPassProvider {

    private static final String GOOGLE_MERCHANT_ID = "3388000000015806310";

    private final GooglePassesClient client;

    @Override
    public void generatePassLink(GreenPass greenPass) {
        var loyaltyClassId = GOOGLE_MERCHANT_ID + "." + greenPass.getNiceIssuer();

        loyaltyClassId = updateLoyaltyClass(loyaltyClassId, greenPass);
        var googlePayId = createLoyaltyObject(loyaltyClassId, greenPass);

        greenPass.setGooglePayLink("https://pay.google.com/gp/v/save/" + generateJwt(googlePayId));
    }

    @SneakyThrows
    public String updateLoyaltyClass(String loyaltyClassId, GreenPass greenPass) {
        var loyaltyClass = new LoyaltyClass();

        loyaltyClass
                .setId(loyaltyClassId)
                .setIssuerName(greenPass.getIssuer())
                .setKind("walletobjects#loyaltyClass")
                .setProgramName("COVID-19 Vaccination")
                .setHexBackgroundColor("#FFFFFF")
                .setProgramLogo(new Image().setSourceUri(new ImageUri().setUri("https://upload.wikimedia.org/wikipedia/en/2/27/EU_flag_square.PNG")))
                .setReviewStatus("underReview");

        loyaltyClass.setClassTemplateInfo(
                new ClassTemplateInfo().setCardTemplateOverride(
                        new CardTemplateOverride().setCardRowTemplateInfos(
                                List.of(new CardRowTemplateInfo().setThreeItems(
                                        new CardRowThreeItems()
                                            .setStartItem(new TemplateItem().setFirstValue(new FieldSelector().setFields(List.of(new FieldReference().setFieldPath("object.textModulesData['firstName']")))))
                                            .setMiddleItem(new TemplateItem().setFirstValue(new FieldSelector().setFields(List.of(new FieldReference().setFieldPath("object.textModulesData['lastName']")))))
                                            .setEndItem(new TemplateItem().setFirstValue(new FieldSelector().setFields(List.of(new FieldReference().setFieldPath("object.textModulesData['dob']")))))
                                        ),
                                        new CardRowTemplateInfo().setThreeItems(
                                                new CardRowThreeItems()
                                                        .setStartItem(new TemplateItem().setFirstValue(new FieldSelector().setFields(List.of(new FieldReference().setFieldPath("object.textModulesData['vaxType']")))))
                                                        .setMiddleItem(new TemplateItem().setFirstValue(new FieldSelector().setFields(List.of(new FieldReference().setFieldPath("object.textModulesData['vaxDoses']")))))
                                                        .setEndItem(new TemplateItem().setFirstValue(new FieldSelector().setFields(List.of(new FieldReference().setFieldPath("object.textModulesData['dov']")))))
                                        )
                                ))));

        try {
            return client.getClient().loyaltyclass().update(loyaltyClassId, loyaltyClass).execute().getId();
        } catch (Exception e) {
            try {
                return client.getClient().loyaltyclass().insert(loyaltyClass).execute().getId();
            } catch (Exception ee) {
                throw new RuntimeException("Failed to insert and update pass", e);
            }
        }
    }

    @SneakyThrows
    private String createLoyaltyObject(String loyaltyClassId, GreenPass greenPass) {
        var loyaltyObject = new LoyaltyObject();
        var objectId = loyaltyClassId + greenPass.getDataNiceId();

        loyaltyObject
                .setId(objectId)
                .setClassId(loyaltyClassId)
                .setState("ACTIVE")
                .setBarcode(new Barcode()
                        .setType("QR_CODE")
                        .setValue(greenPass.getDataPayload())
                        .setAlternateText(greenPass.getDataId()))
                .setTextModulesData(List.of(
                        new TextModuleData().setId("firstName").setHeader("Given name").setBody(greenPass.getFirstName()),
                        new TextModuleData().setId("lastName").setHeader("Family name").setBody(greenPass.getLastName()),
                        new TextModuleData().setId("dob").setHeader("Date of Birth").setBody(greenPass.getDateOfBirth()),
                        new TextModuleData().setId("vaxType").setHeader("Vaccine name").setBody(greenPass.getVaccineType()),
                        new TextModuleData().setId("vaxDoses").setHeader("Doses given").setBody(greenPass.getDosesGiven() + "/" + greenPass.getDosesNeeded()),
                        new TextModuleData().setId("dov").setHeader("Date of vaccination").setBody(greenPass.getDateOfPass())
                ));

        try {
            return client.getClient().loyaltyobject().insert(loyaltyObject).execute().getId();
        } catch (Exception e) {
            try {
                return client.getClient().loyaltyobject().update(objectId, loyaltyObject).execute().getId();
            } catch (Exception ee) {
                throw new RuntimeException("Failed to insert and update pass", e);
            }
        }
    }


    @SneakyThrows
    private String generateJwt(String id) {
        var credentials = client.getCredentials();

        return Jwts.builder()
                .setHeaderParam("kid", credentials.getPrivateKeyId())
                .setHeaderParam("typ", "JWT")
                .setClaims(Map.of(
                        "iss", credentials.getClientEmail(),
                        "aud", "google",
                        "typ", "savetoandroidpay",
                        "iat", System.currentTimeMillis()/1000,
                        "payload", Map.of("loyaltyObjects", List.of(Map.of("id", id))),
                        "origins", List.of("http://thatguyalex.com", "https://thatguyalex.com")
                ))
                .signWith(credentials.getPrivateKey())
                .compact();
    }

}
