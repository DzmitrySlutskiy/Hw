package by.dzmitryslutskiy.hw.data;

/**
 * DataSource
 * Version information
 * 17.10.2014
 * Created by Dzmitry Slutskiy.
 */

public interface DataSource<Result, Params> {

    Result getResult(Params params) throws Exception;
}
