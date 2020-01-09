package com.metlife.nm.carga.adapter.service;

import java.io.File;

public interface MxgesFileInboundService {
	
	 boolean checkChange(File file);
	 void readDocument();

}
