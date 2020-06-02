package vn.byt.qlds.core;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/*dùng gọi api các bảng common*/
public class QldsRestTemplate extends RestTemplate {
    public <T> T putForObject(String url, Object request, Class<T> responseType, Object... uriVariables)
            throws RestClientException {
        RequestCallback requestCallback = httpEntityCallback(httpRequest(request), responseType);
        HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<T>(responseType,
                getMessageConverters());
        return super.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
    }

    private  HttpEntity<Object> httpRequest(Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(object, headers);
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables)
            throws RestClientException {
        return super.postForObject(url, httpRequest(request), responseType, uriVariables);
    }
}
