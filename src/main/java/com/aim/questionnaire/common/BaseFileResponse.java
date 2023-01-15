package com.aim.questionnaire.common;

import com.aim.questionnaire.common.utils.BaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;


/**
 * 文件响应实现层
 *
 * @Author: admin
 * @Date: 2019/5/19 9:36
 */
public class BaseFileResponse implements BaseResponse{

	protected static final Logger logger = LoggerFactory.getLogger(BaseFileResponse.class);
	private HttpServletResponse response;
	private File file;
	private String fileName;
	private String contentType;


	public BaseFileResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setFile(File file) {
	    this.file = file;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public void flush() {
		OutputStream os = null;
	    FileInputStream fis = null;
		try {
			int iSize;
			if (BaseUtil.isNull(this.contentType)) {
				this.fileName = this.file.getName();
			}
			this.response.setDateHeader("Expires", 0L);
			this.response.setHeader("Pragma", "No-cache");
			this.response.setHeader("Cache-Control", "no-cache");
			if (BaseUtil.isNull(this.contentType)) {
				this.contentType = "application/octet-stream";
			}
			this.response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(this.fileName, "UTF-8"));
			os = this.response.getOutputStream();
			fis = new FileInputStream(this.file);
			byte[] buff = new byte[4096];
			while (-1 != (iSize = fis.read(buff))) {
				os.write(buff, 0, iSize);
			}
			os.flush();
		} catch (IOException e) {
			logger.error("Error:", e);
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (os == null) {
				return;
			}
			try {
				os.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
