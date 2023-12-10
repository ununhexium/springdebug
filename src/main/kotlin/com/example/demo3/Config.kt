package com.example.demo3

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.Proxy.Type

val httpProxyHost = "http.proxyHost"
val httpProxyPort = "http.proxyPort"
val httpsProxyHost = "https.proxyHost"
val httpsProxyPort = "https.proxyPort"

@Configuration
class Config {

  @Bean
  fun rt(): RestTemplate {
    val proxy = Proxy(
      Type.HTTP,
      InetSocketAddress(
        System.getProperty(httpProxyHost),
        System.getProperty(httpProxyPort).toInt()
      )
    )

    val requestFactory = SimpleClientHttpRequestFactory()
    requestFactory.setProxy(proxy)

    return RestTemplate(requestFactory)
  }
}
