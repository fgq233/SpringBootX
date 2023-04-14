package com.elasticsearch.demo.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * RestHighLevelClient 注入
 */
@Configurable
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Value("${spring.elasticsearch.rest.uris}")
    private String uris;
    @Value("${spring.elasticsearch.rest.username}")
    private String username;
    @Value("${spring.elasticsearch.rest.password}")
    private String password;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration configuration = ClientConfiguration.builder()
                .connectedTo(uris)
                .withBasicAuth(username, password)
                .build();
        return RestClients.create(configuration).rest();
    }


}
