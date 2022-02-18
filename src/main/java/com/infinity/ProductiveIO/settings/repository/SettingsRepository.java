package com.infinity.ProductiveIO.settings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infinity.ProductiveIO.settings.model.Settings;

public interface SettingsRepository  extends JpaRepository<Settings,Long> {

}
