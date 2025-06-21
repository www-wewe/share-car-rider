package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

public enum FileFormat {
    CSV("CSV", ".csv"),
    PDF("PDF", ".pdf");

    private final String name;
    private final String suffix;

    FileFormat(String name, String suffix) {
        this.name = name;
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
