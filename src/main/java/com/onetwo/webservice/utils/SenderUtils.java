package com.onetwo.webservice.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.webservice.exception.NotExpectResultException;
import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 내부 서버 통신 Utils
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SenderUtils {

    private final WebClient.Builder webClientBuilder;

    private final ObjectMapper objectMapper;

    /**
     * 내부 서버 통신
     *
     * @param method
     * @param uri
     * @param jsonData
     * @param responseClass
     * @param <T>
     * @return
     */
    public <T> T send(HttpMethod method, String uri, Map<String, String> header, Object jsonData, ParameterizedTypeReference<T> responseClass) {
        WebClient client = webClientBuilder.build();
        return retrieve(method, uri, jsonData, makeHeader(header), responseClass);
    }

    /**
     * Make http header
     *
     * @param headerMap
     * @return
     */
    private Consumer<HttpHeaders> makeHeader(Map<String, String> headerMap) {
        Consumer<HttpHeaders> headers = new Consumer<HttpHeaders>() {
            @Override
            public void accept(HttpHeaders t) {
                for (String key : headerMap.keySet()) {
                    t.add(key, headerMap.get(key));
                }
            }
        };
        return headers;
    }

    private <T> T retrieve(HttpMethod method, String uri, Object jsonData, Consumer<HttpHeaders> headers,
                           ParameterizedTypeReference<T> responseClass) {

        long beforeTime = System.currentTimeMillis();
        long afterTime = 0;
        long theSec = 0;

        try {
            WebClient webClient = getWebClient();

            WebClient.RequestHeadersSpec<?> request = webClient
                    .method(method)
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(jsonData == null ? "" : jsonData);

            if (headers != null) {
                request.headers(headers);
            }

            log.info("WebClient Request Start - Request Time : " + beforeTime + " Request Data [" + objectMapper.writeValueAsString(jsonData) + "]");

            Mono<T> mono = request.retrieve().bodyToMono(responseClass);

            T response = mono.block();

            afterTime = System.currentTimeMillis();
            theSec = (afterTime - beforeTime) / 1000;

            log.info("WebClient Response [ Processing time : " + theSec + " End Time : " + afterTime + " ]");

            log.info("response = [" + objectMapper.writeValueAsString(response) + "]");

            return response;

        } catch (WebClientResponseException wre) {
            log.warn("WebClientResponseException", wre);
            /*
             * Get Type
             */
            TypeReference<T> tr = new TypeReference<T>() {
                public Type getType() {
                    return responseClass.getType();
                }
            };
            /*
             * make error Json
             */
            T errorResponse = readExceptionObject(wre, tr);

            afterTime = System.currentTimeMillis();
            theSec = (afterTime - beforeTime) / 1000;

            log.info("WebClient Exception Response [ Processing time : " + theSec + " End Time : " + afterTime + " ]");

            return errorResponse;
        } catch (Exception e) {
            log.error("<ALARM_ERROR>" + "WebClient Exception - [ Request time : " + beforeTime + " ]" + e);

            throw new NotExpectResultException("WebClientRequest Exception" + e);
        }
    }

    private synchronized WebClient getWebClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(8000))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(5000))
                                .addHandlerLast(new WriteTimeoutHandler(5000)))
                .wiretap(this.getClass().getCanonicalName(), LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        ClientHttpConnector conn = new ReactorClientHttpConnector(httpClient);

        return webClientBuilder
                .codecs(clientDefaultCodecsConfigurer -> {
                            clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(getObjectMapper(), MediaType.APPLICATION_JSON));
                            clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(getObjectMapper(), MediaType.APPLICATION_JSON));
                        }
                )
                .clientConnector(conn)
                .build();
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return om;
    }

    private <T> T readExceptionObject(WebClientResponseException wre, TypeReference<T> cls) {
        try {
            return getObjectMapper().readValue(wre.getResponseBodyAsString(Charset.forName("utf-8")), cls);
        } catch (Exception e) {
            log.error("readExceptionObject Exception :" + e);
            return null;
        }
    }
}
