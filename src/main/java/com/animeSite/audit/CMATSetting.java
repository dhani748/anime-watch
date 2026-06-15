package com.animeSite.audit;

import com.animeSite.constant.SettingType;
import com.animeSite.core.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "cmat_setting")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CMATSetting extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cmatId;

    private UUID id;

    @Enumerated(EnumType.STRING)
    private SettingType settingType;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "event_type")
    private String eventType;
}
