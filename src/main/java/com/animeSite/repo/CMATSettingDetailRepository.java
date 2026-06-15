package com.animeSite.repo;

import com.animeSite.audit.CMATSettingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CMATSettingDetailRepository extends JpaRepository<CMATSettingDetail, UUID> {}
