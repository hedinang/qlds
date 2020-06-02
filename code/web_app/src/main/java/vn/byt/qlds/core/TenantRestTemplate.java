package vn.byt.qlds.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class TenantRestTemplate {

    @Autowired
    RestTemplate restTemplate;

    private HttpEntity<Object> httpRequest(String db, Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("session", db);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(object, headers);
    }

    private HttpEntity<Object> httpRequest( Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(object, headers);
    }

    public HttpEntity<Object> httpRequest(String db) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("session", db);
        return new HttpEntity<>(headers);
    }

    public <T> T getForObject(String db, String url, Class<T> responseType) {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(httpRequest(db), responseType);
        HttpMessageConverterExtractor<T> responseExtractor =
                new HttpMessageConverterExtractor<T>(
                        responseType,
                        restTemplate.getMessageConverters());
        return restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor);
    }

    public <T> T getForObject(String db, String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(httpRequest(db), responseType);
        HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(responseType,
                restTemplate.getMessageConverters());
        return restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor, uriVariables);
    }

    public <T> T postForObject(String db, String url, Object request, Class<T> responseType, Object... uriVariables)
            throws RestClientException {
        return restTemplate.postForObject(url, httpRequest(db, request), responseType, uriVariables);
    }

    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables)
            throws RestClientException {
        return restTemplate.postForObject(url, httpRequest(request), responseType, uriVariables);
    }

    public <T> T deleteForObject(String db, String url, Object... uriVariables) throws RestClientException {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(httpRequest(db), Boolean.class);
        HttpMessageConverterExtractor<T> responseExtractor =
                new HttpMessageConverterExtractor<T>(
                        Boolean.class,
                        restTemplate.getMessageConverters());
        return restTemplate.execute(url, HttpMethod.DELETE, requestCallback, responseExtractor, uriVariables);
    }

    public <T> T putForObject(String db, String url, Object request, Class<T> responseType, Object... uriVariables)
            throws RestClientException {

        RequestCallback requestCallback = restTemplate.httpEntityCallback(
                httpRequest(db, request),
                responseType);

        HttpMessageConverterExtractor<T> responseExtractor =
                new HttpMessageConverterExtractor<T>(
                        responseType,
                        restTemplate.getMessageConverters());

        return restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
    }

}
