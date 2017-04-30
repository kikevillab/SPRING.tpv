package config;

public class ResourceNames {

	public static final String PROPERTIES = "classpath:META-INF/application.properties";

	public static final String ASPECT = "aspect";

	public static final String REST_API = "api";

	public static final String CONTROLLERS = "controllers";

	public static final String ENTITIES_USERS = "entities.users";

	public static final String ENTITIES_CORE = "entities.core";

	public static final String DAOS = "daos";

	public static final String DAOS_USERS = "daos.users";

	public static final String DAOS_CORE = "daos.core";

	public static final String SERVICES = "services";

	public static final String YAML_FILES_ROOT = "classpath:META-INF/";

	public static final String DEFAULT_SEED_FILE = "TPV_Database.yml";

	public static final String ADMIN_FILE = "admin.yml";
	
	public final static String PDFS_ROOT = System.getProperty("user.home") + "/tpv/pdfs/";
	
	public final static String PDF_FILE_EXT = ".pdf";
}
