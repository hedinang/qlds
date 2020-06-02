package vn.byt.qlds.service.Report;

import org.springframework.stereotype.Service;
import vn.byt.qlds.core.utils.Config;


/*DÂN SỐ NỮ TỪ 13 TUỔI TRỞ LÊN CHIA THEO
  TÌNH TRẠNG HÔN NHÂN VÀ ĐỊA BÀN DÂN CƯ*/
@Service("DCH04Service")
public class DCH04Service extends DCH04BService {
    @Override
    protected int getSexId() {
        return Config.FEMALE;
    }
}
