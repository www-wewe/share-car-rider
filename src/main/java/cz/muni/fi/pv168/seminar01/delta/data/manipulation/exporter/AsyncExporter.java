package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

import javax.swing.*;
import java.nio.file.Path;
import java.util.Collection;

public class AsyncExporter<T> {

    private final Exporter<T> exporter;

    public AsyncExporter(Exporter<T> exporter){
        this.exporter = exporter;
    }
    public void export(Collection<T> collection, Path filePath) throws ExporterException {
        var asyncWorker = new SwingWorker<>(){

            @Override
            protected Object doInBackground() throws Exception {
                exporter.export(collection, filePath);
                return null;
            }
        };
        asyncWorker.execute();
    }
}
