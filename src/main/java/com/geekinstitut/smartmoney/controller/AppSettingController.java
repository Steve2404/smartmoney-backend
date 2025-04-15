package com.geekinstitut.smartmoney.controller;

import com.geekinstitut.smartmoney.model.AppSetting;
import com.geekinstitut.smartmoney.service.AppSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/app-setting")
@RequiredArgsConstructor
public class AppSettingController {
    private final AppSettingService appSettingService;


    @GetMapping
    public ResponseEntity<List<AppSetting>> getAllAppSettings() {
        return ResponseEntity.ok(appSettingService.getAllAppSettings());
    }


    @GetMapping("/{id}")
    public ResponseEntity<AppSetting> getAppSettingById(@PathVariable UUID id) {
        return appSettingService.getAppSettingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<AppSetting> createAppSetting(@RequestBody AppSetting appSetting) {
        return ResponseEntity.ok(appSettingService.createOrUpdate(appSetting));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppSetting(@PathVariable UUID id) {
        appSettingService.deleteAppSettingById(id);
        return ResponseEntity.noContent().build();
    }

}
