package com.onetwo.webservice.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties
public class PropertiesInfo {

    private ApiGatewayInfo apiGateway = new ApiGatewayInfo();
}

