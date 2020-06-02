package vn.byt.qlds.ministry.configuration;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class ESRestTemplate extends RestTemplate {

    public HttpEntity<Object> httpRequest(Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<Object>(object, headers);
        return request;
    }

    public HttpEntity<Object> httpRequest() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> request = new HttpEntity<>(headers);
        return request;
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) throws RestClientException {
        RequestCallback requestCallback = httpEntityCallback(httpRequest(), responseType);
        HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(responseType,
                getMessageConverters());
        return execute(url, HttpMethod.GET, requestCallback, responseExtractor);
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        RequestCallback requestCallback = httpEntityCallback(httpRequest(), responseType);
        HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(responseType,
                getMessageConverters());
        return execute(url, HttpMethod.GET, requestCallback, responseExtractor);
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables)
            throws RestClientException {
        return super.postForObject(url, httpRequest(request), responseType, uriVariables);
    }
}