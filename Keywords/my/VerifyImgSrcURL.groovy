package my

import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.openqa.selenium.WebElement

/**
 * Uses org.apache.httpcomponents.httpclient_4.5.1.jar bundled in Katalon Studio v8.6.8
 * 
 */
public class VerifyImgSrcURL {
	
	public static boolean doVerify(WebElement imgElement) {
		String urlStr = imgElement.getAttribute("src")
		if (urlStr != null) {
			try {
			    CloseableHttpClient client = HttpClientBuilder.create().build()
			    CloseableHttpResponse response = client.execute(new HttpGet(urlStr))
				return response.getStatusLine().getStatusCode() == 200;
			} catch (Exception e) {
				throw new IOException("failed to get " + urlStr)
			}
		} else {
			throw new IOException("src attribute is not found")
		}
		
	}
}
