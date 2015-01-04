package vitaliy.kuzmich.config;


public class Const {


    public static String URL_ROOT;
    public static String PATH_UPLOADS;
    public static final String PATH_HIBERNATE_PROPS = "/WEB-INF/hibernate.properties";
    public static final String PATH_HIKARI_PROPS = "/WEB-INF/hikari.properties";

    public static final String PATH_VIEWS = "/WEB-INF/views/";
    public static final String SUFFIX_VIEWS = ".jsp";
    public static final String KEY_SUPPORTED_FORMATS = "supported_formats";
    public static final String KEY_ROOT_PATH = "path_suffix";
    public static final int SIZE_UPLOADS_MAX = 100000000;

    public static final String URL_UPLOAD_IMG = "/upload";
    public static final String URL_RESOURCES = "/resources";
    public static final String URL_DOWNLOAD_IMG = URL_UPLOAD_IMG + "s/**";
    public static final String URL_REST_GET_PICS_NAMES = "/list";
    public static String SQL_CREATE_POSITION_MODEL;
    public static final String KEY_TABLE_NAME = ":tableName";
    public static String SQL_INSERT_POSITION_MODEl = "INSERT INTO " + KEY_TABLE_NAME +
            " (" +
            "saveTime,"+
            "accuracy," +
            "altitude," +
            "bearing," +
            "latitude," +
            "longitude," +
            "speed)" +
            "VALUES" +
            "(" +
            "?,"+
            "?," +
            "?," +
            "?," +
            "?," +
            "?," +
            "?)";


}
