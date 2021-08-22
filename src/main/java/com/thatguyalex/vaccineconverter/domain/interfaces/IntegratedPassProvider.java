package com.thatguyalex.vaccineconverter.domain.interfaces;

import com.thatguyalex.vaccineconverter.domain.model.GreenPass;

public interface IntegratedPassProvider {

    void generatePassLink(GreenPass greenPass);

}
