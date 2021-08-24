package com.thatguyalex.vaccineconverter.presentation;

import com.thatguyalex.vaccineconverter.aplication.ProcessRawPass;
import com.thatguyalex.vaccineconverter.presentation.utils.GreenPassToResponseConverter;
import com.thatguyalex.vaccineconverter.presentation.classes.ProcessGreenPassResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SomeController {

    @Autowired
    ProcessRawPass processRawPass;

    @PostMapping("/process")
    public ResponseEntity<ProcessGreenPassResponse> processGreenPass(@NonNull @RequestBody MultipartFile file) {
        return ResponseEntity.ok(GreenPassToResponseConverter.fromGreenPass(processRawPass.convertPdf(file)));
    }

    @GetMapping("/apple/{id}")
    public void getApplePass(@PathVariable String id, HttpServletResponse response) {
        var pass = processRawPass.getApplePass(id);

        response.setStatus(200);
        response.setContentLength(pass.length);
        response.setContentType("application/vnd.apple.pkpass");
        response.setHeader("Content-Disposition","inline; filename=\"vaccine.pkpass\"");
        try {
            response.getOutputStream().write(pass);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write apple pass response", e);
        }
    }

}
