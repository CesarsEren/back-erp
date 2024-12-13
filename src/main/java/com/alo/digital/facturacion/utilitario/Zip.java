package com.alo.digital.facturacion.utilitario;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@Slf4j
public class Zip {
	public InputStream comprimir(File file, String fileName) {
		try {
			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			ZipOutputStream zipOut = new ZipOutputStream(fileOut);
			FileInputStream fileInput = new FileInputStream(file);
			ZipEntry zipEntry = new ZipEntry(file.getName());
			zipOut.putNextEntry(zipEntry);
			byte[] bytes = new byte[1024];
			int length;
			while ((length = fileInput.read(bytes)) >= 0) {
				zipOut.write(bytes, 0, length);
			}
			zipOut.close();
			fileInput.close();
			fileOut.close();
			return new ByteArrayInputStream(fileOut.toByteArray());
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}

	}
}
