package dev.ngb.app.identity.application.dto;

import dev.ngb.domain.identity.model.auth.DeviceType;

public record DeviceInfo(
        DeviceType deviceType,
        String deviceName,
        String fingerprint
) {}
