package com.enesgemci.dubbizle.network


//@Module
//object NetworkModule {
//
//    private const val BASE_URL = "https://api.themoviedb.org/3/"
//
//    /**
//     * Cache size for http response and images
//     */
//    private const val DISK_CACHE_SIZE = 5 * 1024 * 1024 // 5 mb
//
//    @Provides
//    @Singleton
//    fun provideGson(): Gson {
//        return GsonBuilder()
//            .create()
//    }
//
//    @Provides
//    @Singleton
//    fun provideOkhttpClient(app: App): OkHttpClient {
//        val cacheDir = File(app.cacheDir, "http")
//
//        return OkHttpClient.Builder()
//            .addInterceptor(HttpLoggingInterceptor())
//            .cache(Cache(cacheDir, DISK_CACHE_SIZE.toLong()))
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(okHttpClient)
//            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideTMDbApi(retrofit: Retrofit): TMDbApi {
//        return retrofit.create(TMDbApi::class.java)
//    }
//}
