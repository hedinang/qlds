package vn.byt.qlds.core.utils;

public class Config {

    /*status request transfer*/
    public static final int PENDING = 0;
    public static final int ACCEPTED = 1;
    public static final int REJECTED = -1;

    public static final int MALE = 0;
    public static final int FEMALE = 1;

    public static final long TIME_A_YEAR = 31536000000L; // thời gian miliseconse trong 1 năm

    /*marital status*/
    public static final int SINGLE = 1;         /*độc thân*/
    public static final int MARRIED = 2;        /*đã kết hôn có vợ hoặc có chồng*/
    public static final int DIVORCE = 4;        /*ly hôn*/
    public static final int SEPARATE = 5;       /*ly thân*/
    public static final int WIDOW = 6;          /*góa phụ*/
    /*resident status*/
    public static final int THUONG_TRU_CO_MAT = 1;
    public static final int THUONG_TRU_VANG_MAT = 2;
    public static final int TAM_TRU = 3;
    /*change type code*/
    public static final int SUA_SAI = 0;
    public static final int XOA_DO_GHI_THUA = 1;
    public static final int THEM_DO_GHI_THIEU = 2;
    public static final int CHUYEN_DI = 13;
    public static final int CHUYEN_DEN = 14;
    public static final int DIE = 16;
    public static final int TRE_MOI_SINH = 17;
    public static final int KET_HON = 18;
    public static final int KE_HOACH_HOA_GIA_DINH = 31;
    public static final int SUC_KHOE_SINH_SAN = 32;
    public static final int CA_NHAN_CAP_NHAN = 34;
    public static final int SUA_SAI_VAN_CHUA_DUNG = 69;
    public static final String NOTE_DA_LY_HON = "MarCode4";
    public static final String NOTE_DA_KET_HON = "MarCode2";
    /*education*/
    public static final int NOT_WRITE_READ = 1;     /*không biết đọc không biết viết*/
    public static final int PRIMARY_SCHOOL = 2;     /*Trình độ tiểu học*/
    public static final int SECONDARY_SCHOOL = 3;   /*Trình độ THCS*/
    public static final int HIGH_SCHOOL = 4;        /*Trình độ THPT */
    public static final int INTERMADICATE = 5;      /*Trình độ trung cấp */
    public static final int COLLEGE = 6;            /*Trình độ cao đẳng */
    public static final int UNIVERSITY = 7;         /*Trình độ đại học*/
    public static final int GT_UNIVERSITY = 8;      /*Trình độ trên đại học */

    /*type report*/
    public final static String DCX = "DCX";
    public final static String DCH = "DCH";
    public final static String DCT = "DCT";
    public final static String DSX = "DSX";
    public final static String DSH = "DSH";
    public final static String DST = "DST";
    /*type contraceptive*/
    public final static int KHONG_SU_DUNG = 1;
    public final static int VONG_TRANH_THAI = 2;
    public final static int THAY_VONG_TRANH_THAI = 3;
    public final static int THOI_VONG_TRANH_THAI = 4;
    public final static int TRIET_SAN_NAM = 5;
    public final static int TRIET_SAN_NU = 6;
    public final static int BCS = 7;
    public final static int THUOC_UONG = 8;
    public final static int THUOC_TIEM = 9;
    public final static int THUOC_CAY = 10;
    public final static int THAY_THUOC_CAY = 11;
    public final static int THOI_THUOC_CAY = 12;
    public final static int BIEN_PHAP_KHAC = 13;
    /*report dcx*/
    public static final String DCX01 = "DCX01";
    public static final String DCX01_CTV = "DCX01CTV";
    public static final String DCX02 = "DCX02";
    public static final String DCX02_CTV = "DCX02CTV";
    public static final String DCX03 = "DCX03";
    public static final String DCX04 = "DCX04";
    public static final String DCX05 = "DCX05";
    public static final String DCX06 = "DCX06";
    public static final String DCX07 = "DCX07";
    public static final String DCX08 = "DCX08";
    public static final String DCX09 = "DCX09";
    public static final String DCX12 = "DCX12";
    public static final String DCX12B = "DCX12CTV";
    public static final String DCX13 = "DCX13";
    public static final String DCX14 = "DCX14";
    public static final String DCX15 = "DCX15";
    public static final String DCX16 = "DCX16";
    public static final String DCX17 = "DCX17";
    public static final String DCX18 = "DCX18";
    public static final String DCX19 = "DCX19";
    public static final String DCX20 = "DCX20";
    public static final String DCX21 = "DCX21";
    /*report DCH*/
    public static final String DCH01 = "DCH01";
    public static final String DCH02 = "DCH02";
    public static final String DCH03 = "DCH03";
    public static final String DCH04 = "DCH04A";
    public static final String DCH04B = "DCH04B";
    public static final String DCH06 = "DCH06";
    public static final String DCH05 = "DCH05";
    /*dsx*/
    public static final String DSX01 = "DSX01";
    public static final String DSX02 = "DSX02";
    public static final String DSX03 = "DSX03";
    /*dsh*/
    public static final String DSH01 = "DSH01";
    public static final String DSH02 = "DSH02";
    public static final String DSH03 = "DSH03";
    /*dst*/
    public static final String DST01 = "DST01";
    public static final String DST02 = "DST02";
    /*display name sub report*/
    public static final String GTE_AGE = "Từ %d tuổi trở lên";
    public static final String FROM_AGE_TO = "Từ %d đến %d tuổi";
    /*index category*/
    public static final String INDEX_ETH = "nation-category";
    public static final String INDEX_GENDER = "gender";
}
