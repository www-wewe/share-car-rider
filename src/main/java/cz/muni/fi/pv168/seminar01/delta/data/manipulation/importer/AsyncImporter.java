package cz.muni.fi.pv168.seminar01.delta.data.manipulation.importer;

import javax.swing.SwingWorker;
import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Function;

public class AsyncImporter<T> {

    private final Importer<T> importer;
    private final Function<Collection<T>, Void> onFinish;

    public AsyncImporter(Importer<T> importer, Function<Collection<T>, Void> onFinish){
        this.importer = importer;
        this.onFinish = onFinish;
    }

    public void importData(Path filePath) {
        var asyncWorker = new SwingWorker<Collection<T>, Void>(){
            @Override
            protected Collection<T> doInBackground() {
                return importer.importData(filePath);
            }

            @Override
            protected void done() {
                super.done();
                try {
                    onFinish.apply(get());
                } catch (Exception exception){
                    throw new RuntimeException(exception);
                }
            }
        };
        asyncWorker.execute();
    }
}
