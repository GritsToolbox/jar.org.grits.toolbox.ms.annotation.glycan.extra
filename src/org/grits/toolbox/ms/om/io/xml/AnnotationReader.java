package org.grits.toolbox.ms.om.io.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import org.grits.toolbox.ms.om.data.Data;
import org.grits.toolbox.ms.om.data.DataHeader;
import org.grits.toolbox.ms.om.data.GlycanScansAnnotation;
import org.grits.toolbox.ms.om.data.Method;
import org.grits.toolbox.ms.om.data.ScanFeatures;

public class AnnotationReader {
	private static final Logger logger = Logger.getLogger(AnnotationReader.class);

	public ScanFeatures readScanAnnotation(String fileName,int scanId){
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(ScanFeatures.class);
			ScanFeatures scanFeatures = new ScanFeatures();
			File f = extractXMLFile(fileName, scanId+".xml");
			if(f != null && f.exists()){
				jaxbContext = JAXBContext.newInstance(ScanFeatures.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				scanFeatures = (ScanFeatures) jaxbUnmarshaller.unmarshal(f);
				f.delete();
				return scanFeatures;  

			}
			else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Exception in AnnotationReader:readScanAnnotation." , e);
			return null;
		}
	}

	@SuppressWarnings("finally")
	private File extractXMLFile(String filePath,String targetFileName) throws IOException{
		// create a buffer to improve copy performance later.
		byte[] buffer = new byte[2048];

		// open the zip file stream
		InputStream theFile = new FileInputStream(filePath);
		ZipInputStream stream = new ZipInputStream(theFile);
		File outputFile = null;

		try
		{

			// now iterate through each item in the stream. The get next
			// entry call will return a ZipEntry for each file in the
			// stream
			ZipEntry entry;
			while((entry = stream.getNextEntry())!=null)
			{
				
				// Once we get the entry from the stream, the stream is
				// positioned read to read the raw data, and we keep
				// reading until read returns 0 or less.
				if(entry.getName().trim().equalsIgnoreCase(targetFileName)){
					File f = new File(filePath);
					String path = f.getParentFile().getAbsolutePath();
					String outpath = path + File.separator + entry.getName();
					FileOutputStream output = null;
					try
					{
						outputFile = new File(outpath);
						output = new FileOutputStream(outputFile);

						int len = 0;
						while ((len = stream.read(buffer)) > 0)
						{
							output.write(buffer, 0, len);
						}
					}
					finally
					{
						// we must always close the output file
						if(output!=null)
							output.close();
						return outputFile;


					}
				}
			}
		}
		finally
		{
			// we must always close the zip file.
			stream.close();
			if(outputFile == null)
				return null;
			else
				return outputFile;
		}
	}


	public Data readDataWithoutFeatures(String fileName){
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
			Data data = new Data();
			File fData = extractXMLFile(fileName, AnnotationWriter.DATA_FILE);
			jaxbContext = JAXBContext.newInstance(Data.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			data = (Data) jaxbUnmarshaller.unmarshal(fData);
			
			//get the dataHeader object deserialized
			DataHeader dataHeader = readDataHeader(fileName);
			if(dataHeader == null)
				throw new Exception("DataHeader object is null!");
			data.setDataHeader(dataHeader);
			
			//get the method object deserialized
			Method method = readMethod(fileName);
			dataHeader.setMethod(method);
			fData.delete();			
			return data;

		}catch(Exception e){
			e.printStackTrace();
			logger.error("Exception in AnnotationReader:readDataWithoutFeatures." , e);
			return null;
		}
	}

	public Data readData(String fileName){
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
			Data data = new Data();
			File fData = extractXMLFile(fileName, AnnotationWriter.DATA_FILE);
			jaxbContext = JAXBContext.newInstance(Data.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			data = (Data) jaxbUnmarshaller.unmarshal(fData);
			
			fData.delete();			
			return data;

		}catch(Exception e){
			e.printStackTrace();
			logger.error("Exception in AnnotationReader:readDataWithoutFeatures." , e);
			return null;
		}
	}
	
	public Method readMethod(String filePath){
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(Method.class);
			Method method = new Method();
			File f = extractXMLFile(filePath,AnnotationWriter.SETTINGS_FILE);

			if(f != null && f.exists()){
				jaxbContext = JAXBContext.newInstance(Method.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				method = (Method) jaxbUnmarshaller.unmarshal(f);
				f.delete();
				return method;

			}
			else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Exception in AnnotationReader:readMethod." , e);
			return null;
		}
	}

	public DataHeader readDataHeader(String filePath){
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(DataHeader.class);
			DataHeader dataHeader = null;
			File f = extractXMLFile(filePath,AnnotationWriter.DATA_HEADER);

			if(f != null && f.exists()){
				jaxbContext = JAXBContext.newInstance(DataHeader.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				dataHeader = (DataHeader) jaxbUnmarshaller.unmarshal(f);
				f.delete();
				return dataHeader;

			}
			else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Exception in AnnotationReader:readDataHeader." , e);
			return null;
		}
	}

	public GlycanScansAnnotation readglycanAnnotation(String filePath,String glycanId){
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(GlycanScansAnnotation.class);
			GlycanScansAnnotation glycanAnnotations = new GlycanScansAnnotation();
			File f = new File(filePath + File.separator + glycanId + ".xml");
			if(f != null && f.exists()){
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				glycanAnnotations = (GlycanScansAnnotation) jaxbUnmarshaller.unmarshal(f);   
				return glycanAnnotations;  
			}
			else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


}
