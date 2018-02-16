package com.walterjwhite.google.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import java.util.HashSet;
import java.util.Set;

// manual singleton?
public class GuiceHelper {
  private GuiceHelper() {
    super();
  }

  private static Set<AbstractModule> GUICE_MODULES = new HashSet<>();
  private static Injector GUICE_INJECTOR;

  public static void setup() {
    setup(Stage.DEVELOPMENT);
  }

  public static void setup(Stage stage) {
    if (GUICE_INJECTOR == null) {
      GUICE_INJECTOR = Guice.createInjector(stage, GUICE_MODULES);
    }
  }

  public static void addModule(AbstractModule module) {
    GUICE_MODULES.add(module);
  }

  public static void addModules(AbstractModule... modules) {
    for (AbstractModule moduleClass : modules) addModule(moduleClass);
  }

  public static Injector getGuiceInjector() {
    return (GUICE_INJECTOR);
  }

  public static void stop() {
    GUICE_MODULES.clear();
    GUICE_INJECTOR = null;
  }
}
