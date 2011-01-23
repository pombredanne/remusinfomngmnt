import org.remus.infomngmnt.link.webshot.WebshotUtil;



public class LinkUtil {
	public static String getTitleFromUrl(String url) {
		return WebshotUtil.obtainHtmlTitle(url);
	}
}