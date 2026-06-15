package com.animeSite.persist;

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
@Table(name = "setting")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Setting extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "setting_type")
    private SettingType settingType;

    @Column(name = "is_active")
    private Integer isActive = 1;
}
