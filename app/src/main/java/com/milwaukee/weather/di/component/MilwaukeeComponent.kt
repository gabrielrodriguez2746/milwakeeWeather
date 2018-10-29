package com.milwaukee.weather.di.component

import com.milwaukee.weather.base.InjectableApplication
import com.milwaukee.weather.base.di.components.BaseComponent
import com.milwaukee.weather.base.di.modules.AppModule
import com.milwaukee.weather.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        AppModule::class
    ]
)
interface MilwaukeeComponent : BaseComponent {

    @Component.Builder
    interface Builder {

        fun build(): BaseComponent

        @BindsInstance
        fun application(application: InjectableApplication): Builder
    }

}