package com.onetwo.webservice.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.webservice.common.GlobalStatus;
import com.onetwo.webservice.dto.AccessTokenDto;
import com.onetwo.webservice.exception.NotExpectResultException;
import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 내부 서버 통신 Utils
 */
@Component
@Slf4j
public class SenderUtils {

    private final WebClient.Builder webClientBuilder;

    private final ObjectMapper objectMapper;

    private final String accessId;

    private final String accessKey;

    public SenderUtils(WebClient.Builder webClientBuilder,
                       ObjectMapper objectMapper,
                       @Value("${" + GlobalStatus.PROPERTY_ACCESS_ID_LOCATION + "}") String accessId,
                       @Value("${" + GlobalStatus.PROPERTY_ACCESS_KEY_LOCATION + "}") String accessKey) {
        this.webClientBuilder = webClientBuilder;
        this.objectMapper = objectMapper;
        this.accessId = accessId;
        this.accessKey = accessKey;
    }

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
    public <T> ResponseEntity<T> send(HttpMethod method, String uri, Map<String, String> header, Object jsonData, ParameterizedTypeReference<T> responseClass) {
        return retrieve(method, uri, jsonData, makeHeader(header), responseClass);
    }

    public <T> ResponseEntity<T> sendWithHeader(HttpMethod method, String uri, HttpHeaders header, Object jsonData, ParameterizedTypeReference<T> responseClass) {
        Consumer<HttpHeaders> headerConsumer = headers -> {};

        headerConsumer.accept(header);

        return retrieve(method, uri, jsonData, headerConsumer, responseClass);
    }

    /**
     * Make http header
     *
     * @param headerMap
     * @return
     */
    private Consumer<HttpHeaders> makeHeader(Map<String, String> headerMap) {
        return headers -> {
            headers.add(GlobalStatus.ACCESS_ID, accessId);
            headers.add(GlobalStatus.ACCESS_KEY, accessKey);

            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    headers.add(key, headerMap.get(key));
                }
            }
        };
    }

    private <T> ResponseEntity<T> retrieve(HttpMethod method, String uri, Object jsonData, Consumer<HttpHeaders> headers,
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

            log.info("WebClient Request Start - Request Time : {}, Request Uri = {}, Request Data [{}]", beforeTime, uri, objectMapper.writeValueAsString(jsonData));

            ResponseEntity<T> responseEntity = request.retrieve().toEntity(responseClass).block();

            afterTime = System.currentTimeMillis();
            theSec = (afterTime - beforeTime) / 1000;

            log.info("WebClient Response [ Processing time : " + theSec + " End Time : " + afterTime + " ]");

            if (responseEntity.getBody() != null) {
                log.info("response = [" + objectMapper.writeValueAsString(responseEntity.getBody()) + "]");
            }

            return responseEntity;

        } catch (WebClientResponseException wre) {
            log.warn("WebClientResponseException", wre);
            /*
             * Get Type
             */
            TypeReference<T> tr = new TypeReference<T>() {
                @Override
                public Type getType() {
                    return responseClass.getType();
                }
            };
            /*
             * make error Json
             */
            afterTime = System.currentTimeMillis();
            theSec = (afterTime - beforeTime) / 1000;

            log.info("WebClient Exception Response [ Processing time : " + theSec + " End Time : " + afterTime + " ]");

            throw wre;
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

    public Map<String, String> getAccessTokenHeader(AccessTokenDto accessTokenDto) {
        Map<String, String> headers = new HashMap<>();

        headers.put(GlobalStatus.ACCESS_TOKEN, accessTokenDto.getAccessToken());

        return headers;
    }
}
