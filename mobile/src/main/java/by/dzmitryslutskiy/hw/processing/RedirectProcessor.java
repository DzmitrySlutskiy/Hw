package by.dzmitryslutskiy.hw.processing;

/**
 * Classname
 * Version information
 * 21.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class RedirectProcessor<DataSourceResult> implements Processor<DataSourceResult, DataSourceResult> {
    @Override
    public DataSourceResult process(DataSourceResult dataSourceResult) throws Exception {
        return dataSourceResult;
    }
}