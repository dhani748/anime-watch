package com.animeSite.repo;

import com.animeSite.persist.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SettingRepository extends JpaRepository<Setting, UUID> {
    Optional<Setting> findBySettingType(com.animeSite.constant.SettingType settingType);
}
