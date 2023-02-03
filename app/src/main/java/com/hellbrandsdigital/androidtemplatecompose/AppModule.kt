package com.hellbrandsdigital.androidtemplatecompose

import android.content.Context
import com.hellbrandsdigital.homescreen.data.EmailsRepositoryImpl
import com.hellbrandsdigital.navigation.NavigationManager
import com.hellbrandsdigital.utils.SettingsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideNavigationManager() = NavigationManager()

    @Singleton
    @Provides
    fun provideSettingsManager(@ApplicationContext appContext: Context) = SettingsManager(appContext)

    @Singleton
    @Provides
    fun provideEmailsRepository() = EmailsRepositoryImpl()
}
