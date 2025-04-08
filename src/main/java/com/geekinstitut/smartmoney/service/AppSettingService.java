package com.geekinstitut.smartmoney.service;

import com.geekinstitut.smartmoney.model.AppSetting;
import com.geekinstitut.smartmoney.repository.AppSettingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppSettingService {
    private final AppSettingRepository appSettingRepository;

    public List<AppSetting> getAllAppSettings() {
        return appSettingRepository.findAll();
    }

    public Optional<AppSetting> getAppSettingById(UUID id) {
        return appSettingRepository.findById(id);
    }

    public AppSetting createOrUpdate(AppSetting appSetting) {
        return appSettingRepository.save(appSetting);
    }

    public void deleteAppSettingById(UUID id) {
        if (!appSettingRepository.existsById(id)) {
            throw new EntityNotFoundException("AppSetting not found with id: " + id);
        }
        appSettingRepository.deleteById(id);
    }
}
