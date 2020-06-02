package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.model.report.Report;
import vn.byt.qlds.model.report.ReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReportClient {
    @Autowired
    QldsRestTemplate restTemplate;

    private String apiEndpointCommon;

    public ReportClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/report";
    }

    //@Cacheable("report")
    public ReportResponse getReport(String id) {
        String url = this.apiEndpointCommon + "/" + id;
        Report result = restTemplate.getForObject(url, Report.class);
        if (result != null) {
            return new ReportResponse(result.getRcId(), result.getRcName(), result.getLevels(), result.getRptName(),
                    result.getRcType(), result.getProcName(), result.getParas(), result.getShowMonth(), result.getShowQuarter(),
                    result.getShowYear(), result.getShowFromDate(), result.getShowTodate());
        }
        return new ReportResponse();
    }

}
