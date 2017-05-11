package javafish.clients.opc;

import java.io.InputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Basic translator is based on ResourceBundle.
 */
public class Translate {

  private static ResourceBundle resourceBundle;
  private static Properties props;

  private static Locale locale;

  static {
    // load properties
   // props = PropertyLoader.loadProperties(Translate.class.getName());
	  props = new Properties();
	  InputStream in =Translate.class.getResourceAsStream("Translate.properties");
    // set current locale
	  try {
		  props.load(in);
	  } catch (Exception e) {
		
		e.printStackTrace();
	  }
    String lang = props.getProperty("locale");
    if (lang == null || lang.trim().equals("")) {
      locale = Locale.getDefault();
    } else {
      locale = new Locale(props.getProperty("locale"));
    }
    // prepare lang resources
    resourceBundle = ResourceBundle.getBundle(props.getProperty("resource"), locale);
  }

  /**
   * Get translate String
   *
   * @param key String
   * @return translate word String, if not exist: return NULL
   */
  public static String getString(String key) {
    try {
      return resourceBundle.getString(key);
    }
    catch (MissingResourceException e) {
      return null;
    }
  }

  /**
   * Get current locale
   *
   * @return locale Locale
   */
  public static Locale getCurrentLocale() {
    return locale;
  }

}
