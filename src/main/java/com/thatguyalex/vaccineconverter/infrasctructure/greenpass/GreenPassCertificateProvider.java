package com.thatguyalex.vaccineconverter.infrasctructure.greenpass;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import se.digg.dgc.signatures.CertificateProvider;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GreenPassCertificateProvider implements CertificateProvider {

    private List<GreenPassCert> certs = new ArrayList<>();
    private CertificateFactory cf;
    private KeyFactory kf;

    private Pattern countryPattern = Pattern.compile("C=(.*?)(,|$)");

    @SneakyThrows
    public GreenPassCertificateProvider() {
        cf = CertificateFactory.getInstance("X.509");
        kf = KeyFactory.getInstance("EC");

        certs.addAll(addGreenPasses());
        certs.addAll(addNhsPasses());
    }

    @SneakyThrows
    private List<GreenPassCert> addNhsPasses() {
        var certificateDump = new String(this.getClass().getResourceAsStream("/credentials/nhs_certs.json").readAllBytes());
        var certArray = new Gson().fromJson(certificateDump, GreenPassCert.RawNhsPassCert[].class);


        return Arrays.stream(certArray).map(this::certFromRawNhsPass).collect(Collectors.toList());
    }

    @SneakyThrows
    private List<GreenPassCert> addGreenPasses() {
        var certificateDump = new String(this.getClass().getResourceAsStream("/credentials/greenpass_certs.json").readAllBytes());
        var certArray = new Gson().fromJson(certificateDump, GreenPassCert.RawGreenPassCert[].class);

        return Arrays.stream(certArray).map(this::certFromRawGreenPass).collect(Collectors.toList());
    }

    @SneakyThrows
    private GreenPassCert certFromRawNhsPass(GreenPassCert.RawNhsPassCert rawCert) {
        X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(rawCert.getPublicKey()));
        return new GreenPassCert(rawCert.getKid(), "GB", kf.generatePublic(encodedKeySpec));
    }

    @SneakyThrows
    private GreenPassCert certFromRawGreenPass(GreenPassCert.RawGreenPassCert rawCert) {

        var cert = (X509Certificate) cf.generateCertificate(
                new ByteArrayInputStream(Base64.decodeBase64(rawCert.getKey()))
        );
        String country = null;

        var countryMatcher = countryPattern.matcher(cert.getSubjectX500Principal().getName());
        if (countryMatcher.find()) {
            country = countryMatcher.group(1);
        }

        return new GreenPassCert(
                rawCert.getKeyId(),
                country,
                cert.getPublicKey()
        );
    }

    @Override
    public List<PublicKey> getCertificates(String country, byte[] kid) {
        return certs.stream().filter(it -> country.equals(it.getCountry())).map(GreenPassCert::getKey).collect(Collectors.toList());
    }
}
