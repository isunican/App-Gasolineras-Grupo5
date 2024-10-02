package es.unican.gasolineras.injection;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import es.unican.gasolineras.repository.IGasolinerasRepository;
import es.unican.gasolineras.repository.GasolinerasRepository;

/**
 * This class is the provider of @link{IGasolinerasRepository} implementations
 * Any time somebody demands an @link{IGasolinerasRepository} implementation, Hilt will inject the implementation
 * provided by this module
 *
 * InstalllIn: this tells Hilt that this moduke is available to every Activity that is annotated
 * with AndroidEntryPoint. Instead of ActivityComponent.class, I could use SingletonComponent.class,
 * and it seems to work too
 */
@Module
@InstallIn(ActivityComponent.class)
public abstract class RepositoriesModule {

    @Provides
    public static IGasolinerasRepository provideRepository() {
        return GasolinerasRepository.INSTANCE;
    }

}
