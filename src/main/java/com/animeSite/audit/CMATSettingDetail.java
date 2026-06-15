package com.animeSite.audit;

import com.animeSite.core.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "cmat_setting_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CMATSettingDetail extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cmatId;

    private UUID id;

    @Column(name = "setting_id")
    private UUID settingId;

    @Column(name = "setting_key")
    private String settingKey;

    @Column(name = "setting_value", columnDefinition = "TEXT")
    private String settingValue;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "event_type")
    private String eventType;
}
