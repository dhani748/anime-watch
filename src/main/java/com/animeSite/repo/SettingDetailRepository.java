package com.animeSite.repo;

import com.animeSite.persist.SettingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SettingDetailRepository extends JpaRepository<SettingDetail, UUID> {
    List<SettingDetail> findBySettingId(UUID settingId);
    Optional<SettingDetail> findBySettingKey(String settingKey);
}
