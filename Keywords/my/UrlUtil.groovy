package my

import java.net.Proxy
import java.net.HttpURLConnection

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.network.ProxyInformation

import internal.GlobalVariable as GlobalVariable

public class UrlUtil {
	
	/**
	 * checks if a sinble URL is accesible or not.
	 *
	 * @param urlString
	 * @return true if it is accesible, otherwise false
	 */
	@Keyword
	static boolean verifyUrlAccessible(String urlString) {
		URL url = new URL(urlString)
		HttpURLConnection conn
		// Proxyが指定されていたらProxyを通過するように設定する
		ProxyInformation proxyInfo = RunConfiguration.getProxyInformation()
		// TODO configure proxy for HttpURLConnection based on the Katalon's proxy information
		/*
		if (GlobalVariable.G_http_proxy_host && GlobalVariable.G_http_proxy_port) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP,
					new InetSocketAddress(GlobalVariable.G_http_proxy_host,
					Integer.parseInt(GlobalVariable.G_http_proxy_port)));
			conn = (HttpURLConnection) url.openConnection(proxy)
		} else {
			conn = (HttpURLConnection) url.openConnection()
		}
		*/
		conn = (HttpURLConnection) url.openConnection()
		conn.setRequestMethod("GET")
		conn.setConnectTimeout(10000)
		def result
		// URLに接続して成功するかどうかを試す
		try {
			conn.connect()
			if (conn.responseCode == 200
					|| conn.responseCode == 201) {
				// 2xx Success
				result = true
			} else if (conn.responseCode == 301
					|| conn.responseCode == 302
					|| conn.responseCode == 303
					|| conn.responseCode == 307) {
				// 3xx Redirection
				result = true
			} else {
				// 4xx Client Error
				// 5xx Server Error
				result = false
			}
		} catch (IOException e) {
			result = false
		}
		return result
	}
}