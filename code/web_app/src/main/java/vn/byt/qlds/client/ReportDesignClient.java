package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.model.report.ReportDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReportDesignClient {
    @Autowired
    QldsRestTemplate restTemplate;
    private String apiEndpointCommon;

    public ReportDesignClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/report-design";
    }

    public Optional<ReportDesign> getReportDesign(String id) {
        String url = this.apiEndpointCommon + "/" + id;
        ReportDesign result = restTemplate.getForObject(url, ReportDesign.class);
        return Optional.ofNullable(result);
    }
}
