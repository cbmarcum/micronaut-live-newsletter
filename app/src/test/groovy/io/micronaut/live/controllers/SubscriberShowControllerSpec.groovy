package io.micronaut.live.controllers

import io.micronaut.context.annotation.Property
import io.micronaut.core.util.StringUtils
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriBuilder
import io.micronaut.live.Subscriber
import io.micronaut.live.data.PostgresTestPropertyProvider
import io.micronaut.live.data.SubscriberDataRepository
import io.micronaut.live.services.SubscriberSaveService
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@Property(name = "micronaut.security.filter.enabled", value = StringUtils.FALSE)
@MicronautTest(transactional = false)
class SubscriberShowControllerSpec  extends Specification implements PostgresTestPropertyProvider {

    @Inject
    @Client("/")
    HttpClient httpClient

    @Inject
    SubscriberSaveService subscriberSaveService

    @Inject
    SubscriberDataRepository subscriberDataRepository

    void "GET /subscriber/list displays the subscribers HTML page"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        Optional<String> idOptional = subscriberSaveService.saveActiveSubscriber(new Subscriber("tcook@apple.com"))

        then:
        idOptional.isPresent()

        when:
        String id = idOptional.get()
        HttpRequest<?> request = HttpRequest.GET(UriBuilder.of('/subscriber').path(id).build())
                .accept(MediaType.TEXT_HTML)
        String html = client.retrieve(request)

        then:
        noExceptionThrown()
        html
        html.contains('<li>Email: <span>tcook@apple.com</span></li>')

        cleanup:
        subscriberDataRepository.deleteById(id)
    }
}
