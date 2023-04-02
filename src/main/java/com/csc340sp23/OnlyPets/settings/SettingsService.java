package com.csc340sp23.OnlyPets.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    @Autowired
    private SettingsRepository repo;

    public Settings getSettingsByUserId(int userId) {
        return repo.getReferenceById(userId);
    }

    public void save(Settings setting) {
        repo.save(setting);
    }
}