package com.example.demo3

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationContext
import org.springframework.context.event.EventListener
import org.springframework.http.client.support.ProxyFactoryBean
import org.springframework.stereotype.Component

@Component
class ProxySettingsDisplay {
  @Autowired
  lateinit var context: ApplicationContext

  val log = LoggerFactory.getLogger(ProxySettingsDisplay::class.java)

  @EventListener(ApplicationReadyEvent::class)
  fun show() {

    log.info("-".repeat(80))

    listOf(
      "http.proxyHost",
      "http.proxyPort",
      "https.proxyHost",
      "https.proxyPort",
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
      ProxyFactoryBean::class
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

  }
}
