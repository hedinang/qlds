package vn.byt.qlds.sync.core.ES;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BulkServices {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    private BulkProcessor.Listener createBulkListener(){
        return new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                int numberOfActions = request.numberOfActions();
                System.out.println("\nExecuting bulk [{" + executionId + "}] with {" + numberOfActions + "} requests");
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  BulkResponse response) {
                if (response.hasFailures()) {
                    System.out.println("\nBulk [{" + executionId + "}] executed with failures");
                } else {
                    System.out.println("\nBulk [{" + executionId + "}] completed in {" + response.getTook().getMillis() + "} milliseconds");
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  Throwable failure) {
                System.out.println("\nFailed to execute bulk"+failure.getMessage());
            }
        };
    }

    public BulkProcessor createBulkProcessor(){
        BulkProcessor.Builder builder = BulkProcessor.builder(
                (request, bulkListener) ->
                        restHighLevelClient.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
                createBulkListener());
        builder.setBulkActions(10000);
        builder.setBulkSize(new ByteSizeValue(2L, ByteSizeUnit.MB));
        builder.setConcurrentRequests(0);
        builder.setFlushInterval(TimeValue.timeValueSeconds(30L));
        builder.setBackoffPolicy(BackoffPolicy
                .constantBackoff(TimeValue.timeValueSeconds(30L), 3));
        return builder.build();
    }

}
