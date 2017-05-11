package cc.oit.bsmes.interfacePLM.service.impl;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.interfacePLM.dao.JpgfileDAO;
import cc.oit.bsmes.interfacePLM.model.Jpgfile;
import cc.oit.bsmes.interfacePLM.service.JpgfileService;

@Service
public class JpgfileServiceImpl extends BaseServiceImpl<Jpgfile> implements JpgfileService {
	
	@Resource 
	private JpgfileDAO jpgfileDAO;

	@Override
	public List<Jpgfile> getByJpgfileId(String jpgfileId) {
		// TODO Auto-generated method stub
		return jpgfileDAO.getByJpgfileId(jpgfileId);
	}

	@Override
	public String upLoadClImage(File file) throws IOException {
		BufferedInputStream bufferedInputStream=null;		
		try{
			bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
			int size=bufferedInputStream.available();
			byte[] data = new byte[size];
			bufferedInputStream.read(data);
			String jpgfileId="01_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
			Jpgfile jpgfile=new Jpgfile();
			jpgfile.setId(jpgfileId);
			jpgfile.setData(data);
			jpgfileDAO.insertJpgFile(jpgfile);	
			return jpgfileId;
		}finally{
			if(bufferedInputStream!=null){
				bufferedInputStream.close();
			}
		}
	}


	
}
