package by.dzmitryslutskiy.hw.processing;

/**
 * Classname
 * Version information
 * 21.10.2014
 * Created by Dzmitry Slutskiy.
 */
public interface Processor<ProcessingResult, Source> {

    ProcessingResult process(Source source) throws Exception;

}