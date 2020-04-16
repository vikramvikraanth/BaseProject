package com.basecode.app.commonClass.daggerComponent

import android.annotation.SuppressLint
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.basecode.app.commonClass.daggerComponent.viewModelFactory.ViewModelModule
import com.basecode.app.networkApi.ApiInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

@Module(includes = [ViewModelModule::class])
class RetrofitGenerator  {


    @Provides
    fun getRxJavaRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("BuildConfig.baseUrl")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getUnsafeOkHttpClient()!!.build())
            .build()
    }

    /* fun getRxGoogleJavaRetrofit(): Retrofit {
         return Retrofit.Builder()
             .baseUrl(BuildConfig.googleUrl)
             .addConverterFactory(GsonConverterFactory.create())
             .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
             .client(getUnsafeOkHttpClient()!!.build())
             .build()
     }*/

    @Provides
    fun getRetrofitInstance(): ApiInterface {
        val retrofitGenerator = RetrofitGenerator()
        return retrofitGenerator.getRxJavaRetrofit().create(ApiInterface::class.java)
    }

    /* fun getGoogleRetrofitInstance(): ApiInterface {
         val retrofitGenerator = RetrofitGenerator()
         return retrofitGenerator.getRxJavaRetrofit().create(ApiInterface::class.java)
     }*/

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder? {
        return try { // Create a trust manager that does not validate certificate chains
            val trustAllCerts =
                arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )
            /*val interceptor =
                Interceptor { chain: Interceptor.Chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("x-access-token", AppController.token!!)
                        .build()
                    chain.proceed(newRequest)
                }*/
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG)
                logging.level = HttpLoggingInterceptor.Level.BODY
            else
                logging.level = HttpLoggingInterceptor.Level.NONE
            builder.addInterceptor(logging)
            builder.addInterceptor(LoggingInterceptor())
            builder.retryOnConnectionFailure(true)
            builder.writeTimeout(60, TimeUnit.SECONDS)
            builder.connectTimeout(60, TimeUnit.SECONDS)
            builder.readTimeout(60, TimeUnit.SECONDS).build()
            builder.sslSocketFactory(
                sslSocketFactory,
                (trustAllCerts[0] as X509TrustManager)
            )
            builder.hostnameVerifier(HostnameVerifier { hostname: String?, session: SSLSession? -> true })
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
