package com.animeSite.repo;

import com.animeSite.audit.CMATSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CMATSettingRepository extends JpaRepository<CMATSetting, UUID> {}
