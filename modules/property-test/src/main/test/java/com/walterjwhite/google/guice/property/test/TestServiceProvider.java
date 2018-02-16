package com.walterjwhite.google.guice.property.test;

import com.google.inject.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

public class TestServiceProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceProvider.class);

    /*
    @

    public void testServiceProvider(){

    }
    */

    public static void main(final String[] arguments){
        final ServiceLoader<Module> moduleServiceLoader = ServiceLoader.load(Module.class);

        final Iterator<Module> moduleIterator = moduleServiceLoader.iterator();
        while(moduleIterator.hasNext()){
            LOGGER.info("module:" + moduleIterator.next());
        }
    }
}