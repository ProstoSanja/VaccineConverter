package com.thatguyalex.vaccineconverter.infrasctructure.greenpass;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class GreenPassNameProvider {

    private Map<String, GreenPassName> certNames;

    @SneakyThrows
    public GreenPassNameProvider() {
        var nameDump = new String(this.getClass().getResourceAsStream("/certs/vaccines.json").readAllBytes());
        certNames = new Gson().fromJson(nameDump, new TypeToken<Map<String, GreenPassName>>(){}.getType());
    }

    public String getNameForVaccine(String vaccineName) {
        var result = certNames.get(vaccineName);
        if (result == null) {
            log.warn("Unknown vaccine '{}' found, continuing", vaccineName);
            return vaccineName;
        }
        return result.getDisplay();
    }

}
