package com.example.demo3

import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationContext
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.support.ProxyFactoryBean
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI

@Component
class ProxySettingsDisplay(
  val context: ApplicationContext,
  val restTemplate: RestTemplate
) {

  val log = LoggerFactory.getLogger(ProxySettingsDisplay::class.java)!!

  @Order(20)
  @EventListener(ApplicationReadyEvent::class)
  fun show() {

    log.info("-".repeat(80))

    listOf(
      httpProxyHost,
      httpProxyPort,
      httpsProxyHost,
      httpsProxyPort,
    ).forEach { key ->
      try {
        System.getProperty(key)?.let { p ->
          log.info("Found key $key $p")
        } ?: log.warn("Missing key $key")
      } catch (e: Exception) {
        log.error("Failed key $key ${e.message}")
      }
    }

    log.info("-".repeat(80))

    val beans = listOf(
      ProxyFactoryBean::class,
      ClientHttpRequestFactory::class
    )

    beans.forEach { b ->
      try {
        context.getBean(b.java).let {
          log.info("Got ${b.simpleName}: $it")
        }
      } catch (e: Exception) {
        log.warn("No ${b.simpleName}: ${e.message}")
      }
    }
    log.info("-".repeat(80))


    log.info(
      "Rest Template Converters: ${
        restTemplate.messageConverters.joinToString(
          separator = "\n",
          prefix = "\n"
        ) { it.javaClass.simpleName }
      }"
    )

    log.info("-".repeat(80))
  }

  @Order(30)
  @EventListener(ApplicationReadyEvent::class)
  private fun tryQuery() {
    val page = restTemplate.getForEntity(URI("http://example.com/"), String::class.java)

    log.info("-".repeat(80))

    log.info(page.body)

    log.info("-".repeat(80))
  }
}
