package de.kyleonaut.bungeeteleportation.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author kyleonaut
 */
@Getter
@Setter
public class TeleportRequest implements Serializable {
    private UUID player;
    private UUID target;
}
