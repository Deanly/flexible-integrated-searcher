package net.deanly.demo.infrastructure.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ElasticsearchConfig {

    @Value("#{'localhost:9200,localhost:9200'.split(',')}")
    private List<String> endpoints;

    @Bean(value = "restHighLevelClient")
    public RestHighLevelClient getRestHighLevelClient() {

        List<HttpHost> httpHosts = new ArrayList<>();
        for (String endpoint : endpoints) {
            String[] hostAndPort = endpoint.split(":");
            httpHosts.add(new HttpHost(hostAndPort[0], Integer.parseInt(hostAndPort[1]), "http"));
        }

        RestClientBuilder builder = RestClient.builder(httpHosts.toArray(new HttpHost[0]));
        return new RestHighLevelClient(builder);
    }
}
