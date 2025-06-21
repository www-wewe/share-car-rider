package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

public enum DataType {
    RIDES("Jízdy"),
    CATEGORIES("Kategorie");

    private final String data;

    DataType(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.data;
    }
}
