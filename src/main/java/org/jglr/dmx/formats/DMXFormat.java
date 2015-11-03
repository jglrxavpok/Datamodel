package org.jglr.dmx.formats;

public class DMXFormat {

    private String name;
    private int version;

    public DMXFormat() {

    }

    public DMXFormat(String name, int version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(obj instanceof DMXFormat) {
            DMXFormat other = ((DMXFormat) obj);
            return other.getName().equals(getName()) && other.getVersion() == getVersion();
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int base = 31;
        final int multiplier = 47;

        int result = base;
        result = multiplier * result + getName().hashCode();
        result = multiplier * result + getVersion();
        return result;
    }

    @Override
    public String toString() {
        return name+" "+version;
    }
}
