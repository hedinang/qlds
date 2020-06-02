package vn.byt.qlds.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.service.Report.*;


@Component
public class ReportServiceFactory {
    @Autowired
    DCX01Service dcx01Service;
    @Autowired
    DCX02Service dcx02Service;
    @Autowired
    DCX03Service dcx03Service;
    @Autowired
    DCX04Service dcx04Service;
    @Autowired
    DCX05Service dcx05Service;
    @Autowired
    DCX06Service dcx06Service;
    @Autowired
    DCX07Service dcx07Service;
    @Autowired
    DCX08Service dcx08Service;
    @Autowired
    DCX09Service dcx09Service;
    @Autowired
    DCX12Service dcx12Service;
    @Autowired
    DCX12BService dcx12BService;
    @Autowired
    DCX13Service dcx13Service;
    @Autowired
    DCX14Service dcx14Service;
    @Autowired
    DCX15Service dcx15Service;
    @Autowired
    DCX16Service dcx16Service;
    @Autowired
    DCX17Service dcx17Service;
    @Autowired
    DCX18Service dcx18Service;
    @Autowired
    DCX19Service dcx19Service;
    @Autowired
    DCX20Service dcx20Service;
    @Autowired
    DCX21Service dcx21Service;
    @Autowired
    DCX01CTVService dcx01CTVService;
    @Autowired
    DSX01Service dsx01Service;
    @Autowired
    DSX02Service dsx02Service;
    @Autowired
    DSX03Service dsx03Service;
    @Autowired
    DSH01Service dsh01Service;
    @Autowired
    DSH02Service dsh02Service;
    @Autowired
    DSH03Service dsh03Service;
    @Autowired
    DST01Service dst01Service;
    @Autowired
    @Qualifier("DCH04Service")
    DCH04Service dch04Service;
    @Autowired
    @Qualifier("DCH04BService")
    DCH04BService dch04BService;
    @Autowired
    DCH06Service dch06Service;
    @Autowired
    DCH05Service dch05Service;

    //    @Autowired
//    DST02Service dst02Service;

    @Autowired
    DCH01Service dch01Service;
    @Autowired
    DCH02Service dch02Service;
    @Autowired
    DCH03Service dch03Service;

    public ReportService createReportService(String rcID) {
        if (rcID.startsWith(Config.DCX)) return createDCXService(rcID);
        else if (rcID.startsWith(Config.DSX)) return createDSXService(rcID);
        else if (rcID.startsWith(Config.DSH)) return createDSHService(rcID);
        else if (rcID.startsWith(Config.DST)) return createDSTService(rcID);
        else if (rcID.startsWith(Config.DCH)) return createDCHService(rcID);
        else return null;
    }

    private ReportService createDCXService(String rcID) {
        switch (rcID) {
            case Config.DCX01:
                return dcx01Service;
            case Config.DCX01_CTV:
                return dcx01CTVService;
            case Config.DCX02:
                return dcx02Service;
            case Config.DCX03:
                return dcx03Service;
            case Config.DCX04:
                return dcx04Service;
            case Config.DCX05:
                return dcx05Service;
            case Config.DCX06:
                return dcx06Service;
            case Config.DCX07:
                return dcx07Service;
            case Config.DCX08:
                return dcx08Service;
            case Config.DCX09:
                return dcx09Service;
            case Config.DCX12:
                return dcx12Service;
            case Config.DCX12B:
                return dcx12BService;
            case Config.DCX13:
                return dcx13Service;
            case Config.DCX14:
                return dcx14Service;
            case Config.DCX15:
                return dcx15Service;
            case Config.DCX16:
                return dcx16Service;
            case Config.DCX17:
                return dcx17Service;
            case Config.DCX18:
                return dcx18Service;
            case Config.DCX19:
                return dcx19Service;
            case Config.DCX20:
                return dcx20Service;
            case Config.DCX21:
                return dcx21Service;
            default:
                return null;
        }
    }

    private ReportService createDCHService(String rcID) {
        switch (rcID) {
            case Config.DCH01:
                return dch01Service;
            case Config.DCH02:
                return dch02Service;
            case Config.DCH03:
                return dch03Service;
            case Config.DCH04:
                return dch04Service;
            case Config.DCH04B:
                return dch04BService;
            case Config.DCH06:
                return dch06Service;
            case Config.DCH05:
                return dch05Service;
            default:
                return null;
        }
    }

    private ReportService createDSXService(String rcID) {
        switch (rcID) {
            case Config.DSX01:
                return dsx01Service;
            case Config.DSX02:
                return dsx02Service;
            case Config.DSX03:
                return dsx03Service;
            default:
                return null;
        }
    }

    private ReportService createDSHService(String rcID) {
        switch (rcID) {
            case Config.DSH01:
                return dsh01Service;
            case Config.DSH02:
                return dsh02Service;
            case Config.DSH03:
                return dsh03Service;
            default:
                return null;
        }
    }

    private ReportService createDSTService(String rcID) {
        switch (rcID) {
            case Config.DST01:
                return dst01Service;
//            case Config.DST02:
//                return dst02Service;
            default:
                return null;
        }
    }
}
