package org.jglr.dmx;

import java.util.UUID;
import javax.annotation.Nullable;

public class Element extends AttributeList {

    private final Datamodel owner;
    private final String name;
    private final String className;
    private final UUID uuid;

    public Element(Datamodel owner) {
        this(owner, "Stub element", null);
    }

    public Element(Datamodel owner, String name) {
        this(owner, "Stub element", "DmElement", null);
    }

    public Element(Datamodel owner, String name, String className) {
        this(owner, name, className, null);
    }

    public Element(Datamodel owner, String name, String className, @Nullable UUID uuid) {
        super(owner);
        this.owner = owner;
        this.name = name;
        this.className = className;
        if(uuid == null) {
            this.uuid = UUID.randomUUID();
        } else {
            this.uuid = uuid;
        }
    }

    public String getClassName() {
        return className;
    }

    public String getName() {
        return name;
    }

    public Datamodel getOwner() {
        return owner;
    }

    public UUID getUuid() {
        return uuid;
    }
}
