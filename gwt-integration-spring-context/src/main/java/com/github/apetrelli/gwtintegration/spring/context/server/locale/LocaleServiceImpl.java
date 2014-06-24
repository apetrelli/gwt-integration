package com.github.apetrelli.gwtintegration.spring.context.server.locale;

import java.util.Locale;

import org.springframework.stereotype.Service;

import com.github.apetrelli.gwtintegration.locale.server.LocaleLocator;

@Service
public class LocaleServiceImpl implements LocaleService {

    @Override
    public void setLocale(String localeCode) {
        LocaleLocator.getHolder().setLocale(new Locale(localeCode));
    }

}
