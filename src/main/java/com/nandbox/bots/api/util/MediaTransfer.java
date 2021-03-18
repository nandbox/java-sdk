package com.nandbox.bots.api.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Calendar;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.nandbox.bots.api.NandboxClient;
import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class MediaTransfer {

	private MediaTransfer() {
		throw new IllegalStateException("Utility class");
	}

	private static final String CONTENT_TYPE = "application/json";
//	private static final String BEARER_TOKENS_PREFIX = "Bearer ";

	/**
	 * downloadFile it will return 0 when file saved successfully
	 */

	/**
	 * @param token
	 *            Bot API Token to be used to download the file
	 * @param mediaFileId
	 *            Media File Id to be download e.g. photoId , VideoId,....
	 * @param savingDirPath
	 *            null to save it in current directory or the local directory path
	 *            to save file
	 * @param savingFileName
	 *            null to save downloaded media with name mediaId or a file name be
	 *            used to save the downloaded media .
	 * @return 0 in success case , 1: if fail to download the media
	 */
	public static int downloadFile(String token, String mediaFileId, String savingDirPath,
			String savingFileName) {

		int result = -1;
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			String downloadServerURL = NandboxClient.getConfigs().getProperty("DownloadServer");

			HttpRequestBase httpGetRequest = new HttpGet(downloadServerURL + mediaFileId);

			RequestConfig.Builder requestConfig = RequestConfig.custom();
			requestConfig.setConnectTimeout(40 * 1000);
			requestConfig.setConnectionRequestTimeout(40 * 1000);
			requestConfig.setSocketTimeout(40 * 1000);
			httpGetRequest.setConfig(requestConfig.build());
			//System.out.println("Executing request " + httpGetRequest.getRequestLine());
			NandboxClient.log.info("Executing request " + httpGetRequest.getRequestLine());

			httpGetRequest.setHeader("Content-type", CONTENT_TYPE);
			httpGetRequest.setHeader("X-TOKEN", token);
//			httpGetRequest.setHeader("Authorization", BEARER_TOKENS_PREFIX + token);
			long downloadStartTime = Calendar.getInstance().getTimeInMillis();
			HttpResponse response = httpclient.execute(httpGetRequest);
			long downloadEndTime = Calendar.getInstance().getTimeInMillis();
			//System.out.println("Download File : " + mediaFileId + " took around "
			//		+ (downloadEndTime - downloadStartTime) / 1000 + " Seconds");
			NandboxClient.log.info("Download File : " + mediaFileId + " took around "
					+ (downloadEndTime - downloadStartTime) / 1000 + " Seconds");

			savingDirPath = savingDirPath != null ? savingDirPath : "./"; // If savingDirPath is
																			// null , assuming
																			// current Director
			savingFileName = savingFileName != null ? savingFileName : mediaFileId;
			String mediaFileFullPath = savingDirPath + File.separator + savingFileName;
			inputStream = response.getEntity().getContent();
			outputStream = new FileOutputStream(mediaFileFullPath);
			IOUtils.copy(inputStream, outputStream);
			//System.out.println("File Saved Locally Successfully");
			NandboxClient.log.info("File Saved Locally Successfully");
			result = 0;

		} catch (IOException e) {
			//e.printStackTrace();
			NandboxClient.log.error(e.getStackTrace());
			result = -1;

		} finally {

			try {
				if (inputStream != null) {
					inputStream.close();
				}

				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				//e.printStackTrace();
				NandboxClient.log.error(e.getStackTrace());
			}

		}

		//System.out.println("Result =" + result);
		NandboxClient.log.info("Result =" + result);
		return result;
	}

	/**
	 * @param token
	 *            Bot API Token to be used to download the file
	 * @param mediaFileFullPath
	 *            local directory path to upload the file
	 * @return Upload File it will return Attachment as string
	 */
	public static String uploadFile(String token, String mediaFileFullPath) {

		String media = null;
		String output = null;
		StringBuilder sb = new StringBuilder();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {

			File file = new File(mediaFileFullPath);
			String fileContentType = null;
			try {
				fileContentType = Files.probeContentType(file.toPath());
			} catch (IOException e) {
				//e.printStackTrace();
				//System.out.println();
				NandboxClient.log.error(e.getStackTrace());
			}

			//System.out.println("fileContentType " + fileContentType);
			NandboxClient.log.info("fileContentType " + fileContentType);

			String uploadServerURL = NandboxClient.getConfigs().getProperty("UploadServer");
			HttpRequestBase httpputrequest = new HttpPut(uploadServerURL + file.getName());
			RequestConfig.Builder requestConfig = RequestConfig.custom();
			requestConfig.setConnectTimeout(40 * 1000);
			requestConfig.setConnectionRequestTimeout(40 * 1000);
			requestConfig.setSocketTimeout(200 * 1000);
			httpputrequest.setConfig(requestConfig.build());
			httpputrequest.setHeader("Content-type", fileContentType);
//			httpputrequest.setHeader("Authorization", BEARER_TOKENS_PREFIX + token);
			httpputrequest.setHeader("X-TOKEN", token);

			FileEntity fileEntity = new FileEntity(file);

			((HttpPut) httpputrequest).setEntity(fileEntity);

			//System.out.println("executing request " + httpputrequest.getRequestLine());
			NandboxClient.log.info("executing request " + httpputrequest.getRequestLine());
			CloseableHttpResponse response = null;
			try {
				long uploadStartTime = Calendar.getInstance().getTimeInMillis();
				response = httpclient.execute(httpputrequest);
				long uploadEndTime = Calendar.getInstance().getTimeInMillis();
				//System.out.println("Upload File : " + mediaFileFullPath + " took "
				//		+ (uploadEndTime - uploadStartTime) / 1000 + " Seconds");
				NandboxClient.log.info("Upload File : " + mediaFileFullPath + " took "
								+ (uploadEndTime - uploadStartTime) / 1000 + " Seconds");
			} catch (IOException e) {
				//e.printStackTrace();
				NandboxClient.log.error(e.getStackTrace());
			}

			if (response != null) {

				if (response.getStatusLine().getStatusCode() == 200
						|| response.getStatusLine().getStatusCode() == 204) {

					BufferedReader br = null;
					try {
						br = new BufferedReader(
								new InputStreamReader((response.getEntity().getContent())));
					} catch (UnsupportedOperationException | IOException e) {
						//e.printStackTrace();
						NandboxClient.log.error(e.getStackTrace());
					}

					//System.out.println("Output from Server ...."
					//		+ response.getStatusLine().getStatusCode() + "\n");
					NandboxClient.log.info("Output from Server ...."
							+ response.getStatusLine().getStatusCode() + "\n");

					if (br != null) {

						while ((output = br.readLine()) != null) {
							//System.out.println("output " + output);
							NandboxClient.log.info("output " + output);

							sb.append(output);
						}

					}
				} else {
					//System.out.println(response.getStatusLine().getStatusCode());
					NandboxClient.log.info(response.getStatusLine().getStatusCode());
				}

				if (Utils.isNotEmpty(sb.toString())) {

					JSONObject obj = (JSONObject) JSONValue.parse(sb.toString());

					media = String.valueOf(obj.get("file"));
				}

				//System.out.println("Uploaded Media File ID is : " + media);
				NandboxClient.log.info("Uploaded Media File ID is : " + media);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			NandboxClient.log.error(e.getStackTrace());
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				//e.printStackTrace();
				NandboxClient.log.error(e.getStackTrace());
			}
		}
		return media;
	}

}