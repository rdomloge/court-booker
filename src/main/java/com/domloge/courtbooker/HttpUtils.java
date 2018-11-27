package com.domloge.courtbooker;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.domloge.courtbooker.domain.Court;
import com.domloge.courtbooker.domain.TimeSlot;

@Component
public class HttpUtils {
	
	private static final String TIMETABLE = 
			"https://basingstokesc.legendonlineservices.co.uk/basingstoke/mobile/gettimetable?facilityId=1&bookingType=1&activityId=35";

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private static final String LOGIN_PAGE = "https://basingstokesc.legendonlineservices.co.uk/basingstoke/account/login";
	
	private static final String LOGOUT_PAGE = "https://basingstokesc.legendonlineservices.co.uk/basingstoke/mobile/logout?Area=";
	
	private static final String PAY_PAGE = "https://basingstokesc.legendonlineservices.co.uk/basingstoke/basket/pay";
	

	private HttpContext ctx;
	
	private CloseableHttpClient client;
	
	public HttpUtils() {
		CookieStore cookieStore = new BasicCookieStore();
		ctx = HttpClientContext.create();
		ctx.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
		
		RequestConfig config = RequestConfig.custom()
				  .setConnectTimeout(5000)
				  .setConnectionRequestTimeout(5000)
				  .setSocketTimeout(5000).build();
		
		client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}
	
	public void login() throws IOException {
		logger.info("Loading login page for cookie");
		try(CloseableHttpResponse response = client.execute(new HttpGet(LOGIN_PAGE), ctx)) {
			
		}
		
		logger.info("Posting login form");
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("login.Email", "rdomloge@gmail.com"));
		formparams.add(new BasicNameValuePair("login.Password", "482aa85375a623cd8bbcf5cf01ca97c5"));
		formparams.add(new BasicNameValuePair("login.HashedPassword", "False"));
		formparams.add(new BasicNameValuePair("login.IgnoreCookies", "True"));
		formparams.add(new BasicNameValuePair("login.Remember", "true"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		HttpPost post = new HttpPost(LOGIN_PAGE);
		post.setEntity(entity);
		try(CloseableHttpResponse response = client.execute(post, ctx)) {
			int code = response.getStatusLine().getStatusCode();
			logger.info("Form posted: "+(302 == code ? "success" : "failed"));			
		}
	}
	
	public String loadTimetable() throws ClientProtocolException, IOException {
		logger.info("Loading timetable");
		return load(TIMETABLE);
	}
	
	public String loadCourts(TimeSlot slot) throws IOException {
		logger.info("Loading courts for slot "+slot);
		// https://basingstokesc.legendonlineservices.co.uk/basingstoke/bookingscentre/availablecourts
		// ?activityId=35&facilityId=1&starttime=2018-02-14%2017:00&endtime=2018-02-14%2017:40
		String base = "https://basingstokesc.legendonlineservices.co.uk/basingstoke/bookingscentre/availablecourts";
		String constantParams = "?activityId=35&facilityId=1";
		String startTime = slot.getDate().toString("YYYY-MM-dd") + "%20" + slot.getStartTime().toString("HH:mm");
		String endTime = slot.getDate().toString("YYYY-MM-dd") + "%20" + slot.getEndTime().toString("HH:mm");
		String variableParams = "&starttime="+startTime+"&endtime="+endTime;
		return load(new StringBuilder(base+constantParams+variableParams).toString());
	}
	
	private String load(String url) throws IOException {
		logger.info("Loading "+url);
		int attempts = 1;
		while(attempts < 3) {
			try(CloseableHttpResponse response = client.execute(new HttpGet(url), ctx)) {
				InputStream inputStream = response.getEntity().getContent();
				String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
				String printable = text.length() > 50 ? text.substring(0, 50)+" ...}" : text;
				logger.info("JSON: "+printable);
				return text;
			}
			catch(SocketTimeoutException stex) {
				logger.error("Timed out loading "+url);
			}
		}
		throw new IOException("Too many retries");
	}
	
	public String bookCourt(TimeSlot slot, Court court) throws IOException {
		//https://basingstokesc.legendonlineservices.co.uk/basingstoke/bookingscentre/addsportshallbooking
		//?slotId=749&selectedCourts=16
		String base = "https://basingstokesc.legendonlineservices.co.uk/basingstoke/bookingscentre/addsportshallbooking";
		String params = "?slotId="+slot.getId()+"&selectedCourts="+court.getSectorReference();
		return load(base+params);
	}
	
	public CommitResult commit() throws IOException {
		try(CloseableHttpResponse response = client.execute(new HttpGet(PAY_PAGE), ctx)) {
			int code = response.getStatusLine().getStatusCode();
			boolean success = (200 == code ? true : false);
			logger.info("Booking result: {}, due to {}", success, code);
			return new CommitResult(response.getStatusLine().getReasonPhrase(), code, success);
		}
	}
	
	public void logout() throws IOException {
		load(LOGOUT_PAGE);
	}
}
