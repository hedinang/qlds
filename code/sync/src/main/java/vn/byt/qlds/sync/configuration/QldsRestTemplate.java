package vn.byt.qlds.sync.configuration;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

public class QldsRestTemplate extends RestTemplate {

    public HttpEntity<Object> httpRequest(Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
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


    public <T> T deleteForObject(String url, Object... uriVariables) throws RestClientException {
        RequestCallback requestCallback = httpEntityCallback(httpRequest(), Boolean.class);
        HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(Boolean.class,
                getMessageConverters());
        return super.execute(url, HttpMethod.DELETE, requestCallback, responseExtractor);
    }


    public <T> T putForObject(String url, Object request, Class<T> responseType, Object... uriVariables)
            throws RestClientException {
        RequestCallback requestCallback = httpEntityCallback(httpRequest(request), responseType);
        HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(responseType,
                getMessageConverters());
        return super.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
    }


}