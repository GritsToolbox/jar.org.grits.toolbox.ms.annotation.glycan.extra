package org.grits.toolbox.ms.om.io.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.log4j.Logger;

import org.grits.toolbox.ms.om.data.Annotation;
import org.grits.toolbox.ms.om.data.Data;
import org.grits.toolbox.ms.om.data.DataHeader;
import org.grits.toolbox.ms.om.data.Feature;
import org.grits.toolbox.ms.om.data.GlycanAnnotation;
import org.grits.toolbox.ms.om.data.GlycanFeature;
import org.grits.toolbox.ms.om.data.GlycanScansAnnotation;
import org.grits.toolbox.ms.om.data.Method;
import org.grits.toolbox.ms.om.data.ScanFeatures;

public class AnnotationWriter {
	private static final Logger logger = Logger.getLogger(AnnotationWriter.class);

	public final static String DATA_FILE = "data.xml";
	public final static String DATA_HEADER = "dataHeader.xml";
	public final static String SETTINGS_FILE = "settings.xml";

	public void createAnnZipFilePerScan(Data data) throws IOException{
		try{

			JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
			JAXBContext jaxbContextAnn = JAXBContext.newInstance(ScanFeatures.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			Marshaller jaxbMarshallerAnn = jaxbContextAnn.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//compress the annotations	
			Iterator<Entry<Integer, ScanFeatures>> it = data.getScanFeatures().entrySet().iterator();
			while(it.hasNext()){
				Entry<Integer, ScanFeatures> item = it.next();
				int scanId = item.getKey();
				if(scanId != 1){
					File f = new File("./zip/"+scanId+".xml");
					//write the file to the archive file
					jaxbMarshallerAnn.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					//jaxbMarshallerAnn.marshal(data.getScanFeatures().get(scanId), compressedFile);
					jaxbMarshallerAnn.marshal(data.getScanFeatures().get(scanId), f);
					it.remove();
					//compressedFile.closeEntry();	
				}
			}
		}catch(Exception ex){
			logger.error("Exception in AnnotationWriter:createAnnZipFilePerScan." , ex);
		}

	}

	public void writeAnnotationsPerGlycan(GlycanScansAnnotation annotations,String path) throws IOException{
		try{

			JAXBContext jaxbContext = JAXBContext.newInstance(GlycanScansAnnotation.class);

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			File f = new File(path + File.separator + annotations.getGlycanId() + ".xml");
			//write the file to the archive fil
			//jaxbMarshallerAnn.marshal(data.getScanFeatures().get(scanId), compressedFile);
			jaxbMarshaller.marshal(annotations, f);
		}catch(Exception ex){
			logger.error("Exception in AnnotationWriter:writeAnnotationsPerGlycan." , ex);
		}
	}

	public void writeDataHeaderToZip( DataHeader dHeader, ZipOutputStream compressedFile ) {
		JAXBContext jaxbDataHeaderContext = null;
		Marshaller jaxbDataHeaderMarshaller = null;
		try {
			jaxbDataHeaderContext = JAXBContext.newInstance(DataHeader.class);
			jaxbDataHeaderMarshaller = jaxbDataHeaderContext.createMarshaller();
		} catch (JAXBException e1) {
			logger.error(e1.getMessage(), e1);
			return;
		} catch ( Exception e2 ) {
			logger.error(e2.getMessage(), e2);
			return;
		}
		try {
			jaxbDataHeaderMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			compressedFile.putNextEntry(new ZipEntry(AnnotationWriter.DATA_HEADER));
			jaxbDataHeaderMarshaller.marshal(dHeader, compressedFile);
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
		} catch (JAXBException e2) {
			logger.error(e2.getMessage(), e2);
		} catch ( Exception ex ) {
			logger.error(ex.getMessage(), ex);
		}
		try {
			compressedFile.closeEntry();
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
		} catch ( Exception ex ) {
			logger.error(ex.getMessage(), ex);
		}

	}

	public void writeMethodToZip( Method method, ZipOutputStream compressedFile ) {
		JAXBContext jaxbMethodContext = null;
		Marshaller jaxbMethodMarshaller = null;
		try {
			jaxbMethodContext = JAXBContext.newInstance(Method.class);
			jaxbMethodMarshaller = jaxbMethodContext.createMarshaller();
		} catch (JAXBException e1) {
			logger.error(e1.getMessage(), e1);
			return;
		} catch ( Exception e2 ) {
			logger.error(e2.getMessage(), e2);
			return;
		}
		try {
			jaxbMethodMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			compressedFile.putNextEntry(new ZipEntry(AnnotationWriter.SETTINGS_FILE));
			jaxbMethodMarshaller.marshal(method, compressedFile);
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
		} catch (JAXBException e2) {
			logger.error(e2.getMessage(), e2);
		} catch ( Exception ex ) {
			logger.error(ex.getMessage(), ex);
		}
		try {
			compressedFile.closeEntry();
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
		} catch ( Exception ex ) {
			logger.error(ex.getMessage(), ex);
		}		
	}

	public void writeDataToZip(Data data, ZipOutputStream compressedFile ) {
		JAXBContext jaxbDataContext = null;
		Marshaller jaxbDataMarshaller = null;
		try {
			jaxbDataContext = JAXBContext.newInstance(Data.class);
			jaxbDataMarshaller = jaxbDataContext.createMarshaller();
		} catch (JAXBException e1) {
			logger.error(e1.getMessage(), e1);
			return;
		} catch ( Exception e2 ) {
			logger.error(e2.getMessage(), e2);
			return;
		}
		try {
			jaxbDataMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			compressedFile.putNextEntry(new ZipEntry(AnnotationWriter.DATA_FILE));
			// testing not serializing the scanfeatures in the data object.
			HashMap<Integer, ScanFeatures> hm = data.getScanFeatures();
			data.setScanFeatures(null);
			jaxbDataMarshaller.marshal(data, compressedFile);
			data.setScanFeatures(hm);
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
		} catch (JAXBException e2) {
			logger.error(e2.getMessage(), e2);
		} catch ( Exception ex ) {
			logger.error(ex.getMessage(), ex);
		}
		try {
			compressedFile.closeEntry();
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
		} catch ( Exception ex ) {
			logger.error(ex.getMessage(), ex);
		}		
	}

	public void writeScanFeaturesToZip(Data data, ZipOutputStream compressedFile, String glycanFilesPath ) throws Exception {
		JAXBContext jaxbContextAnn = null;
		Marshaller jaxbMarshallerAnn = null;
		try {
			jaxbContextAnn = JAXBContext.newInstance(ScanFeatures.class);
			jaxbMarshallerAnn = jaxbContextAnn.createMarshaller();
		} catch (JAXBException e1) {
			logger.error(e1.getMessage(), e1);
			return;
		} catch ( Exception e2 ) {
			logger.error(e2.getMessage(), e2);
			return;
		}
		HashMap<String,String> gogCheckerForMS1 = new HashMap<String,String>();
		try {
			if( data.getScans() != null ) {
				for(Integer scanId : data.getScans().keySet()){
					try {
						compressedFile.putNextEntry(new ZipEntry(scanId+".xml"));
						jaxbMarshallerAnn.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						jaxbMarshallerAnn.marshal(data.getScanFeatures().get(scanId),compressedFile);
					} catch (IOException e1) {
						logger.error(e1.getMessage(), e1);
					} catch (JAXBException e2) {
						logger.error(e2.getMessage(), e2);
					} catch ( Exception ex ) {
						logger.error(ex.getMessage(), ex);
					}
					try {
						compressedFile.closeEntry();
						if(data.getAnnotatedScan().get(scanId) != null){
							for(String gog : data.getAnnotatedScan().get(scanId)){
								gogCheckerForMS1.put(gog, "seen");
							}
						}
					} catch (IOException e1) {
						logger.error(e1.getMessage(), e1);
					} catch ( Exception ex ) {
						logger.error(ex.getMessage(), ex);
					}
				}
			}
		} catch ( Exception ex ) {
			logger.error(ex.getMessage(), ex);
			return;
		}

		try {
			deleteAnnotationsPerGlycan(glycanFilesPath,gogCheckerForMS1);
		} catch ( Exception ex ) {
			logger.error(ex.getMessage(), ex);
		}
	}

	public static ZipOutputStream getZipOutputStream( String zipPath ) {
		// the zipPath better already have ".zip" appended!
		FileOutputStream ms_final;
		try {
			ms_final = new FileOutputStream(zipPath);
			ZipOutputStream compressedFile = new ZipOutputStream(ms_final);
			return compressedFile;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static String getArchiveFilePath( String _sPreArchiveFilePath ) {
		return _sPreArchiveFilePath + ".zip";
	}

	public void generateScansAnnotationFiles(String glycanFilesPath, Data data, String zipPath, 
			boolean bWriteHeader, boolean bWriteMethod, boolean bWriteData, boolean bWriteScanFeatures) throws IOException{
		// the zipPath better already have ".zip" appended!
		ZipOutputStream compressedFile = null;
		try {
			compressedFile = AnnotationWriter.getZipOutputStream(zipPath);
		}catch(Exception ex){
			logger.error("Error initializing zip output stream for path: " + zipPath, ex);
			return;
		}
		try {
			if( bWriteHeader ) {
				writeDataHeaderToZip(data.getDataHeader(), compressedFile);
			}
			if( bWriteMethod ) {
				writeMethodToZip(data.getDataHeader().getMethod(), compressedFile);
			}
			if( bWriteData ) {
				writeDataToZip(data, compressedFile);
			}
			if( bWriteScanFeatures ) {
				writeScanFeaturesToZip(data, compressedFile, glycanFilesPath);
			}
		}catch(Exception ex){
			logger.error("Exception in AnnotationWriter:generateScansAnnotationFiles." , ex);
		}
		try {
			compressedFile.close();
		}catch(Exception ex){
			logger.error("Error closing compressed file", ex);
		}
	}

	public void writeSingleScanToZipFile(int scanId,ScanFeatures scanFeatures,String zipFilePath){
		try{
			String zipFolder = new File(zipFilePath).getParentFile().getAbsolutePath();
			JAXBContext jaxbContextAnn = JAXBContext.newInstance(ScanFeatures.class);		    
			Marshaller jaxbMarshallerAnn = jaxbContextAnn.createMarshaller();
			//compress the annotations	
			ZipFile zipFile = new ZipFile(zipFilePath);
			zipFile.removeFile(scanId+".xml");
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			File scanFile = new File(zipFolder + File.separator + scanId + ".xml");
			//write the file to the archive file
			jaxbMarshallerAnn.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//jaxbMarshallerAnn.marshal(data.getScanFeatures().get(scanId), compressedFile);
			jaxbMarshallerAnn.marshal(scanFeatures,scanFile);
			zipFile.addFile(scanFile, parameters);
		}catch(Exception ex){
			logger.error("Exception in AnnotationWriter:writeSingleScanToZipFile." , ex);
		}
	}

	public void deleteAllTempFiles(String path) throws IOException{
		try{

			File dir = new File(path);
			for(String gog : dir.list()){
				File f = new File(gog);
				f.delete();
			}

		}catch(Exception ex){
			logger.error("Exception in AnnotationWriter:deleteAnnotationsPerGlycan." , ex);
		}

	}

	public void deleteAnnotationsPerGlycan(String path,HashMap<String,String> gogs) throws IOException{
		try{
			for(String gog : gogs.keySet()){
				try {
					File f = new File(path + File.separator + gog +".xml");
					f.delete();
				} catch( Exception e ) {
					logger.error(e.getMessage(), e);
				}
			}

		}catch(Exception ex){
			logger.error("Exception in AnnotationWriter:deleteAnnotationsPerGlycan." , ex);
		}

	}

	public void writeDataHeaderToArchive(DataHeader dHeader,String zipFilePath){
		try{
			String zipFolder = new File(zipFilePath).getParentFile().getAbsolutePath();
			JAXBContext jaxbContextDataHeader = JAXBContext.newInstance(DataHeader.class);		    
			Marshaller jaxbMarshallerAnn = jaxbContextDataHeader.createMarshaller();
			//compress the annotations	
			ZipFile zipFile = new ZipFile(zipFilePath);
			zipFile.removeFile(AnnotationWriter.DATA_HEADER);
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			File headerFile = new File(zipFolder + File.separator + AnnotationWriter.DATA_HEADER);
			//write the file to the archive file
			jaxbMarshallerAnn.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshallerAnn.marshal(dHeader,headerFile);
			zipFile.addFile(headerFile, parameters);
			headerFile.delete();
		} catch(Exception ex) {
			logger.error("Exception in AnnotationWriter:writeDataHeaderToArchive." , ex);
		}
	}

	public void writeDataToZip( Data data, String zipFilePath ) {
		try{
			String zipFolder = new File(zipFilePath).getParentFile().getAbsolutePath();
			JAXBContext jaxbContextData = JAXBContext.newInstance(Data.class);		    
			Marshaller jaxbMarshallerAnn = jaxbContextData.createMarshaller();
			//compress the annotations	
			ZipFile zipFile = new ZipFile(zipFilePath);
			zipFile.removeFile(AnnotationWriter.DATA_FILE);
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			File dataFile = new File(zipFolder + File.separator + AnnotationWriter.DATA_FILE);
			//write the file to the archive file
			jaxbMarshallerAnn.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//jaxbMarshallerAnn.marshal(data.getScanFeatures().get(scanId), compressedFile);
			jaxbMarshallerAnn.marshal(data,dataFile);
			zipFile.addFile(dataFile, parameters);
			dataFile.delete();
		} catch(Exception ex) {
			logger.error("Exception in AnnotationWriter:writeDataToZip." , ex);
		}
	}
	public void convertScanAnnotationFilesToGlycanAnnotationFiles(String zipFileName,String outputPath){
		try{
			AnnotationReader reader = new AnnotationReader();
			Data data = reader.readDataWithoutFeatures(zipFileName);
			HashMap<Integer,GlycanAnnotation> glycanAnnotationIds = getGlycanAnnotationIds(data);
			for(Integer annId : glycanAnnotationIds.keySet()){
				System.out.println("Now Processing Glycan " + glycanAnnotationIds.get(annId).getGlycanId());
				List<Integer> scanIds = getScanIds(glycanAnnotationIds.get(annId),data);
				GlycanScansAnnotation gsa = new GlycanScansAnnotation();
				for(Integer scanId : scanIds){
					ScanFeatures sf = reader.readScanAnnotation(zipFileName, scanId);
					gsa.setAnnotationId(annId);
					gsa.setGlycanId(glycanAnnotationIds.get(annId).getGlycanId());
					List<GlycanFeature> glycanFeatures = new ArrayList<GlycanFeature>();
					for(Feature feature : sf.getFeatures()){
						GlycanFeature f = (GlycanFeature)feature;
						if(f.getAnnotationId().equals(annId))
							glycanFeatures.add(f);
					}
					gsa.getScanAnnotations().put(scanId, glycanFeatures);	
				}
				System.out.println("Now done Glycan " + glycanAnnotationIds.get(annId).getGlycanId());
				writeAnnotationsPerGlycan(gsa, outputPath);
			}
		}catch(Exception e){
			logger.error("Exception in AnnotationWriter:convertScanAnnotationFilesToGlycanAnnotationFiles." , e);
		}
	}

	public void convertScanAnnotationFilesToGlycanAnnotationFilesManuallySelected(String zipFileName,String outputPath){
		try{
			AnnotationReader reader = new AnnotationReader();
			Data data = reader.readDataWithoutFeatures(zipFileName);
			ScanFeatures scan1Fs = reader.readScanAnnotation(zipFileName, 1);
			HashMap<Long,List<Integer>> selectedAnnotations = new HashMap<Long,List<Integer>>();
			selectedAnnotations = getSelectedAnnotations(scan1Fs);
			for(Long key : selectedAnnotations.keySet())
				System.out.println(key);
			HashMap<Integer,GlycanAnnotation> glycanAnnotationIds = getGlycanAnnotationIds(data);
			for(Integer annId : glycanAnnotationIds.keySet()){
				System.out.println("Now Processing Glycan " + glycanAnnotationIds.get(annId).getGlycanId());
				List<Integer> scanIds = getScanIds(glycanAnnotationIds.get(annId),data);
				GlycanScansAnnotation gsa = new GlycanScansAnnotation();
				//gsa = new GlycanScansAnnotation();
				for(Integer scanId : scanIds){
					System.out.println("going to check the selected glycans with: " + selectedAnnotations.keySet().size()+":"+annId+":"+scanId);
					if(!selectedGlycan(selectedAnnotations,annId,scanId,data))
						continue;
					System.out.println("Now found a selected scan");
					ScanFeatures sf = reader.readScanAnnotation(zipFileName, scanId);
					gsa.setAnnotationId(annId);
					gsa.setGlycanId(glycanAnnotationIds.get(annId).getGlycanId());
					List<GlycanFeature> glycanFeatures = new ArrayList<GlycanFeature>();
					for(Feature feature : sf.getFeatures()){
						GlycanFeature f = (GlycanFeature)feature;
						if(f.getAnnotationId().equals(annId))
							glycanFeatures.add(f);
					}
					gsa.getScanAnnotations().put(scanId, glycanFeatures);	

					//if the scan has subscans add them to the annotations
					if(data.getScans().get(scanId) !=  null && data.getScans().get(scanId).getSubScans().size() != 0){
						for(Integer subScanId : data.getScans().get(scanId).getSubScans()){
							System.out.println("Now found a Subscan");
							sf = reader.readScanAnnotation(zipFileName, subScanId);
							if(sf !=null ){
								System.out.println("^^Now found annotated SubScan^^");
								gsa.setAnnotationId(annId);
								gsa.setGlycanId(glycanAnnotationIds.get(annId).getGlycanId());
								glycanFeatures = new ArrayList<GlycanFeature>();
								for(Feature feature : sf.getFeatures()){
									GlycanFeature f = (GlycanFeature)feature;
									if(f.getAnnotationId().equals(annId))
										glycanFeatures.add(f);
								}
								gsa.getScanAnnotations().put(subScanId, glycanFeatures);	
							}
						}
					}
				}
				System.out.println("Now done Glycan " + glycanAnnotationIds.get(annId).getGlycanId());
				//check if this glycan got selected otherwise ignore it
				if(gsa.getScanAnnotations().keySet().size() != 0){
					System.out.println("Now writing annotation");
					writeAnnotationsPerGlycan(gsa, outputPath);
				}
			}
		}catch(Exception e){
			logger.error("Exception in AnnotationWriter:convertScanAnnotationFilesToGlycanAnnotationFiles." , e);
		}
	}

	private boolean selectedGlycan(HashMap<Long,List<Integer>>selectedAnnotations,int annId,int scanId,Data data){
		Long mz = Math.round(data.getScans().get(scanId).getPrecursor().getMz());
		System.out.println("mz is : " + mz);
		if(selectedAnnotations.get(mz)!=null){
			System.out.println("mz is found selected ");
			for(Integer id : selectedAnnotations.get(mz)){
				System.out.println("id: " + id + " annId: " + annId);
				if(id == annId){
					System.out.println("id: is equal to annId: ");
					return true;
				}else{
					System.out.println("id: is not equal to annId: ");
				}
			}
		}
		return false;
	}
	
	private HashMap<Long,List<Integer>> getSelectedAnnotations(ScanFeatures sf){
		HashMap<Long,List<Integer>> selectedScans = new HashMap<Long,List<Integer>>();
		for(Feature feature : sf.getFeatures()){
			if(feature.getManuallySelected()){
				if(selectedScans.get(Math.round(feature.getMz())) == null){
					List<Integer> gogs = new ArrayList<Integer>();
					gogs.add(feature.getAnnotationId());
					selectedScans.put(Math.round(feature.getMz()), gogs);
					System.out.println("featureMz:" + feature.getMz());
				}else{
					selectedScans.get(Math.round(feature.getMz())).add(feature.getAnnotationId());
				}
			}
		}
		System.out.println("selected scans = " + selectedScans.size());
		return selectedScans;

	}

	public HashMap<Integer,GlycanAnnotation> getGlycanAnnotationIds(Data data){
		HashMap<Integer,GlycanAnnotation> glycanAnnIds = new HashMap<Integer,GlycanAnnotation>();
		for(Annotation ann : data.getAnnotation()){
			GlycanAnnotation gan = (GlycanAnnotation) ann;
			glycanAnnIds.put(gan.getId(), gan);
		}
		return glycanAnnIds;
	}

	public List<Integer> getScanIds(GlycanAnnotation ann,Data data){
		List<Integer> ids = new ArrayList<Integer>();
		for(String scanId : ann.getScores().keySet()){
			ids.add(Integer.parseInt(scanId));
			//			if(data.getScans().get(Integer.parseInt(scanId)) !=  null && data.getScans().get(Integer.parseInt(scanId)).getSubScans().size() != 0)
			//				ids.addAll(data.getScans().get(Integer.parseInt(scanId)).getSubScans());
		}
		return ids;
	}

	public static void main(String[] args) throws IOException{
		AnnotationWriter writer = new AnnotationWriter();
		writer.convertScanAnnotationFilesToGlycanAnnotationFiles("C:\\Users\\aljadda\\Documents\\GRITS_tester\\firstTest\\glycan_annotation\\7725.zip","C:\\Users\\aljadda\\Documents\\GRITS_tester\\firstTest\\");

	}


}
