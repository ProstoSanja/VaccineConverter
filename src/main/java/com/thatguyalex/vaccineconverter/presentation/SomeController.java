package com.thatguyalex.vaccineconverter.presentation;

import com.thatguyalex.vaccineconverter.aplication.ProcessRawPass;
import com.thatguyalex.vaccineconverter.presentation.utils.GreenPassToResponseConverter;
import com.thatguyalex.vaccineconverter.presentation.classes.ProcessGreenPassResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SomeController {

    @Autowired
    ProcessRawPass processRawPass;

    @PostMapping("/process")
    public ProcessGreenPassResponse processGreenPass(@NonNull @RequestBody MultipartFile file) {
        return GreenPassToResponseConverter.fromGreenPass(processRawPass.convertPdf(file));
    }

}
