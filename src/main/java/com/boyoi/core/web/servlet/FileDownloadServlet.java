package com.boyoi.core.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件下载Servlet；接收文件名称，将文件以IO流方式发送给客户端
 * newFileName传参，代表文件名为指定的文件名
 * @author Chenj
 */
public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = -6769457777686291183L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

        // 请求的文件名
		String fileName = new String(request.getParameter("file").trim().getBytes("ISO-8859-1"), "utf-8");

        // 指定文件名
		String newFileName = new String(request.getParameter("newFileName").trim().getBytes("ISO-8859-1"), "utf-8");

		if (!"".equals(fileName)) {
			//组织文件路径
			String filePath = request.getSession().getServletContext().getRealPath("/")+ File.separator + fileName;
			File file = new File(filePath);
			if (file.exists()) {
				//下载设置
				response.reset();// 重设response
				response.setContentType("application/x-msdownload");// 输出类型：文件下载
				response.setCharacterEncoding("UTF-8");//设置编码格式
				response.addHeader("Content-Length", "" + file.length());//设置显示文件大小
				if(!"".equals(newFileName)){
                    response.addHeader("Content-Disposition", "attachment;filename=\"" + new String(newFileName.getBytes("UTF-8"), "ISO-8859-1"));//重设文件名
				}else{
                    response.addHeader("Content-Disposition", "attachment;filename=\"" + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));//原文件名
				}

				//IO方式向客户端发送文件数据
				InputStream in = new FileInputStream(file);
				OutputStream out = response.getOutputStream();
				byte[] buffer = new byte[2048];
				while (in.read(buffer)>0) {
					out.write(buffer);
				}
				in.close();
				out.flush();
				out.close();
			} else {
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.println("错误：文件不存在！&nbsp;&nbsp;<a href='JavaScript:history.go(-1);'>返 回</a>");
                out.flush();
                out.close();
			}
		} else {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println("错误：文件路径不能为空！&nbsp;&nbsp;<a href='JavaScript:history.go(-1);'>返 回</a>");
            out.flush();
            out.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet (req, resp);
	}

}
